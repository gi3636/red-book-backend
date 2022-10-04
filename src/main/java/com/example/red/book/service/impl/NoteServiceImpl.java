package com.example.red.book.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.common.api.CommonPage;
import com.example.red.book.common.api.ElasticSearchResult;
import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.common.service.RedisService;
import com.example.red.book.constant.NoteConstant;
import com.example.red.book.entity.Note;
import com.example.red.book.manager.NoteManager;
import com.example.red.book.mapper.NoteMapper;
import com.example.red.book.model.doc.NoteDoc;
import com.example.red.book.model.form.NoteAddForm;
import com.example.red.book.model.form.NoteQueryForm;
import com.example.red.book.model.form.NoteSearchForm;
import com.example.red.book.model.form.NoteUpdateForm;
import com.example.red.book.model.vo.NoteVO;
import com.example.red.book.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author franky
 * @since 2022-09-17
 */
@Slf4j
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

    @Autowired
    private ElasticsearchClient esClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisService redisService;

    @Autowired
    NoteManager noteManager;

    private static final String indexName = NoteConstant.INDEX;


    @Override
    public Boolean add(NoteAddForm noteAddForm, Long userId) {
        Note note = new Note();
        note.setUserId(userId);
        note.setTitle(noteAddForm.getTitle());
        note.setContent(noteAddForm.getContent());
        note.setIsPublic(noteAddForm.getIsPublic());
        note.setImages(String.join(",", noteAddForm.getImages()));
        Boolean isSuccess = this.baseMapper.insert(note) > 0;
        if (isSuccess) {
            rabbitTemplate.convertAndSend(NoteConstant.EXCHANGE_NAME, NoteConstant.INSERT_KEY, note);
        }
        return isSuccess;
    }

    @Override
    public CommonPage<NoteVO> query(NoteQueryForm noteQueryForm) {
        Page<Note> data = noteManager.getNotePage(noteQueryForm);
        Page<NoteVO> newData = new Page<>();
        newData.setRecords(NoteVO.convert(data.getRecords()));
        newData.setTotal(data.getTotal());
        newData.setCurrent(data.getCurrent());
        newData.setSize(data.getSize());
        newData.setPages(data.getPages());
        return CommonPage.restPage(newData);
    }

    public Boolean addEsDoc(NoteDoc noteDoc) {
        IndexRequest<NoteDoc> request = new IndexRequest.Builder<NoteDoc>()
                .index(indexName)
                // 设置文档id
                .id(noteDoc.getId() + "")
                // 设置文档
                .document(noteDoc)
                // 刷新
                .refresh(Refresh.True)
                .build();
        try {
            IndexResponse response = esClient.index(request);
            log.info("增加笔记es文档: {} ", response.result());
            return response.result() != null;
        } catch (IOException e) {
            log.error("增加笔记es文档错误: {} ", e.getMessage(), e);
            return false;
        }
    }


    public Boolean updateEsDoc(NoteDoc noteDoc) {
        UpdateRequest<NoteDoc, NoteDoc> request = new UpdateRequest.Builder<NoteDoc, NoteDoc>()
                .id(noteDoc.getId() + "")
                .index(indexName)
                .doc(noteDoc)
                .build();
        try {
            UpdateResponse<NoteDoc> fileDocumentGetResponse = esClient.update(request, NoteDoc.class);
            log.info("更新笔记es文档 : {}", fileDocumentGetResponse.result());
            return true;
        } catch (IOException e) {
            log.error("更新笔记es文档错误 : {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Note selectByUserIdAndTitle(Long userId, String title) {
        return noteManager.getNote(userId, title);
    }

    @Override
    public Boolean update(NoteUpdateForm noteUpdateForm, Long userId) {
        Note note = new Note();
        note.setId(noteUpdateForm.getId());
        note.setUserId(userId);
        note.setTitle(noteUpdateForm.getTitle());
        note.setContent(noteUpdateForm.getContent());
        note.setIsPublic(noteUpdateForm.getIsPublic());
        note.setImages(noteUpdateForm.getImages());
        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<Note>();
        wrapper.eq(Note::getId, noteUpdateForm.getId())
                .eq(Note::getUserId, userId);
        Boolean isSuccess = this.baseMapper.update(note, wrapper) > 0;
        if (isSuccess) {
            rabbitTemplate.convertAndSend(NoteConstant.EXCHANGE_NAME, NoteConstant.UPDATE_KEY, note);
        }
        return isSuccess;
    }

    @Override
    public Note selectById(Long id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public ElasticSearchResult<NoteDoc> search(NoteSearchForm noteSearchForm) {
        return noteManager.getNoteByEs(noteSearchForm);
    }

    @Override
    public Boolean like(Long noteId, Long userId) {
        String key = userId + "::" + noteId;
        Note note = baseMapper.selectById(noteId);
        if (note == null){
            throw GlobalException.from(ResultCode.NOTE_NOT_EXIST);
        }
        try {
            redisService.hSet(NoteConstant.USER_NOTE_LIKE_KEY, key, 1);
            increaseLikeCount(noteId);
            return true;
        } catch (Exception e) {
            log.error("点赞失败: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean unlike(Long noteId, Long userId) {
        String key = userId + "::" + noteId;
        Note note = baseMapper.selectById(noteId);
        if (note == null){
            throw GlobalException.from(ResultCode.NOTE_NOT_EXIST);
        }
        try {
            redisService.hSet(NoteConstant.USER_NOTE_LIKE_KEY, key, 0);
            decreaseLikeCount(noteId);
            return true;
        } catch (Exception e) {
            log.error("取消点赞失败: {}", e.getMessage(), e);
            return false;
        }
    }

    public Boolean increaseLikeCount(Long noteId) {
        try {
            redisService.hIncr(NoteConstant.USER_NOTE_LIKE_COUNT_KEY, noteId + "", 1L);
            return true;
        } catch (Exception e) {
            log.error("增加点赞数失败: {}", e.getMessage(), e);
            return false;
        }
    }

    public Boolean decreaseLikeCount(Long noteId) {
        try {
            redisService.hDecr(NoteConstant.USER_NOTE_LIKE_COUNT_KEY, noteId + "", 1L);
            return true;
        } catch (Exception e) {
            log.error("减少点赞数失败: {}", e.getMessage(), e);
            return false;
        }
    }
}
