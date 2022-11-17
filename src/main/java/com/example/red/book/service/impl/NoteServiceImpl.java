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
import com.example.red.book.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    private static final String indexName = NoteConstant.INDEX;
    @Autowired
    NoteManager noteManager;
    @Autowired
    private ElasticsearchClient esClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;


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
    public CommonPage<NoteVO> query(NoteQueryForm noteQueryForm, Long selfId) {
        Page<NoteVO> data = noteManager.getNoteVOPage(noteQueryForm, selfId);
        return CommonPage.restPage(data);
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
    public Boolean update(Note note) {
        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<Note>();
        wrapper.eq(Note::getId, note.getId())
                .eq(Note::getUserId, note.getUserId());
        Boolean isSuccess = this.baseMapper.update(note, wrapper) > 0;
        if (isSuccess) {
            //rabbitTemplate.convertAndSend(NoteConstant.EXCHANGE_NAME, NoteConstant.UPDATE_KEY, note);
        }
        return isSuccess;
    }

    @Override
    public Note selectById(Long id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public CommonPage<NoteVO> search(NoteSearchForm noteSearchForm, Long userId) {
        ElasticSearchResult<NoteDoc> noteList = noteManager.getNoteByEs(noteSearchForm);
        List<NoteVO> noteVoList = new ArrayList<>();
        Page<NoteVO> noteVOPage = new Page<>();
        for (NoteDoc noteDoc : noteList.getList()) {
            NoteVO noteVO = this.baseMapper.selectNote(noteDoc.getId(), userId);
            noteVoList.add(noteVO);
        }
        noteVOPage.setRecords(noteVoList);
        noteVOPage.setTotal(noteList.getTotal());
        noteVOPage.setSize(noteList.getPageSize());
        noteVOPage.setCurrent(noteList.getPageNum());
        return CommonPage.restPage(noteVOPage);
    }

    @Override
    public CommonPage<NoteVO> queryRecommend(Long selfId) {
        Page<NoteVO> page = new Page<>(1, 20);
        Page<NoteVO> noteVOPage = this.baseMapper.selectNoteList(page, null, true, selfId);
        for (NoteVO noteVO : noteVOPage.getRecords()) {
            noteVO.setImageList(noteVO.getImages() == null ? new ArrayList<>() : Arrays.asList((noteVO.getImages().split(","))));
            noteVO.setImages(null);
        }
        return CommonPage.restPage(noteVOPage);
    }

    @Override
    public CommonPage<NoteVO> queryLike(NoteQueryForm noteQueryForm, Long selfId) {
        Page<NoteVO> data = noteManager.getLikedNoteVOPage(noteQueryForm, selfId);
        return CommonPage.restPage(data);
    }

}
