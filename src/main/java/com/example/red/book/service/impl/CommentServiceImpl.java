package com.example.red.book.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.common.api.CommonPage;
import com.example.red.book.common.api.ElasticSearchResult;
import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.constant.CommentConstant;
import com.example.red.book.constant.NoteConstant;
import com.example.red.book.entity.Comment;
import com.example.red.book.entity.Note;
import com.example.red.book.manager.CommentManager;
import com.example.red.book.mapper.CommentMapper;
import com.example.red.book.model.doc.CommentDoc;
import com.example.red.book.model.form.CommentAddForm;
import com.example.red.book.model.form.CommentQueryForm;
import com.example.red.book.model.vo.CommentVO;
import com.example.red.book.service.CommentService;
import com.example.red.book.service.NoteService;
import com.example.red.book.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2022-09-19
 */
@Slf4j
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private static final String indexName = NoteConstant.INDEX;
    @Autowired
    NoteService noteService;
    @Autowired
    UserService userService;
    @Autowired
    CommentManager commentManager;
    @Autowired
    private ElasticsearchClient esClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Boolean add(CommentAddForm commentAddForm, Long userId) {
        Note note = noteService.selectById(commentAddForm.getNoteId());
        if (note == null) {
            throw GlobalException.from(ResultCode.NOTE_NOT_EXIST);
        }

        if (commentAddForm.getToUserId() != null) {
            if (userService.selectById(commentAddForm.getToUserId()) == null) {
                throw GlobalException.from(ResultCode.USER_NOT_FOUND);
            }
        }
        //没有parent_id则为一级评论
        if (commentAddForm.getParentId() != null) {
            Comment parentComment = baseMapper.selectById(commentAddForm.getParentId());
            if (parentComment == null) {
                throw GlobalException.from(ResultCode.COMMENT_NOT_EXIST);
            }
            if (!Objects.equals(parentComment.getNoteId(), commentAddForm.getNoteId())) {
                throw GlobalException.from(ResultCode.COMMENT_NOTE_NOT_MATCH);
            }
        }

        Comment comment = new Comment();
        comment.setNoteId(commentAddForm.getNoteId());
        comment.setContent(commentAddForm.getContent());
        comment.setToUserId(commentAddForm.getToUserId());
        comment.setParentId(commentAddForm.getParentId());
        comment.setUserId(userId);
        Boolean isSuccess = this.save(comment);
        if (isSuccess) {
            rabbitTemplate.convertAndSend(CommentConstant.EXCHANGE_NAME, CommentConstant.INSERT_KEY, comment);
        }
        return isSuccess;
    }

    @Override
    public CommonPage<CommentVO> query(CommentQueryForm commentQueryForm, Long userId) {
        Note note = noteService.selectById(commentQueryForm.getNoteId());
        if (note == null) {
            throw GlobalException.from("笔记不存在");
        }
        //笔记不公开，只有作者可以查看
        if (!note.getIsPublic() && !note.getUserId().equals(userId)) {
            throw GlobalException.from("笔记不公开");
        }
        //查询一级评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getNoteId, commentQueryForm.getNoteId())
                .isNull(Comment::getParentId);
        Page<Comment> page = new Page<>(commentQueryForm.getCurrentPage(), commentQueryForm.getSize());
        Page<Comment> commentPage = this.page(page, queryWrapper);
        List<Comment> comments = commentPage.getRecords();
        List<CommentVO> commentVOList = CommentVO.convert(comments);
        for (CommentVO comment : commentVOList) {
            //查询二级评论
            LambdaQueryWrapper<Comment> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.eq(Comment::getNoteId, commentQueryForm.getNoteId())
                    .eq(Comment::getParentId, comment.getId());
            List<Comment> comments2 = this.list(queryWrapper2);
            List<CommentVO> commentVOList2 = CommentVO.convert(comments2);
            comment.setChildren(commentVOList2);
        }
        Page<CommentVO> commentVOPage = new Page<>(commentQueryForm.getCurrentPage(), commentQueryForm.getSize());
        commentVOPage.setTotal(commentPage.getTotal());
        commentVOPage.setPages(commentPage.getPages());
        commentVOPage.setRecords(commentVOList);
        return CommonPage.restPage(commentVOPage);

    }

    @Override
    public void addEsDoc(CommentDoc commentDoc) {
        if (commentDoc.getParentId() != null) {
            CommentDoc parentComment = commentManager.getCommentDocByEs(commentDoc.getParentId());
            if (parentComment != null) {
                parentComment.getChildren().add(commentDoc);
                this.updateEsDoc(parentComment);
            }
        } else {
            IndexRequest<CommentDoc> request = new IndexRequest.Builder<CommentDoc>()
                    .index(indexName)
                    .id(commentDoc.getId() + "")
                    .document(commentDoc)
                    .refresh(Refresh.True)
                    .build();
            try {
                IndexResponse response = esClient.index(request);
                log.info("增加评论es文档: {} ", response.result());
            } catch (IOException e) {
                log.error("增加评论es文档错误: {} ", e.getMessage(), e);
            }
        }

    }

    @Override
    public ElasticSearchResult<CommentDoc> queryByEs(CommentQueryForm commentQueryForm, Long userId) {
        Note note = noteService.selectById(commentQueryForm.getNoteId());
        if (note == null) {
            throw GlobalException.from("笔记不存在");
        }
        //笔记不公开，只有作者可以查看
        if (!note.getIsPublic() && !note.getUserId().equals(userId)) {
            throw GlobalException.from("笔记不公开");
        }
        return commentManager.getCommentListByEs(commentQueryForm.getNoteId(), commentQueryForm.getCurrentPage(), commentQueryForm.getSize());
    }


    public void updateEsDoc(CommentDoc commentDoc) {
        UpdateRequest<CommentDoc, CommentDoc> request = new UpdateRequest.Builder<CommentDoc, CommentDoc>()
                .id(commentDoc.getId() + "")
                .index(indexName)
                .doc(commentDoc)
                .build();
        try {
            UpdateResponse<CommentDoc> fileDocumentGetResponse = esClient.update(request, CommentDoc.class);
            log.info("更新笔记es文档 : {}", fileDocumentGetResponse.result());
        } catch (IOException e) {
            log.error("更新笔记es文档错误 : {}", e.getMessage(), e);
        }
    }
}
