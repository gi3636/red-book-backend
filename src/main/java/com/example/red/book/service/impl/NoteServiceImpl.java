package com.example.red.book.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.common.api.CommonPage;
import com.example.red.book.common.api.ElasticSearchResult;
import com.example.red.book.constant.NoteMqConstant;
import com.example.red.book.entity.Note;
import com.example.red.book.manager.NoteManager;
import com.example.red.book.mapper.NoteMapper;
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
    NoteManager noteManager;

    private static final String indexName = "note";


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
            rabbitTemplate.convertAndSend(NoteMqConstant.EXCHANGE_NAME, NoteMqConstant.INSERT_KEY, note.getId());
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

    public Boolean addDoc(NoteVO noteVO) {
        IndexRequest<NoteVO> request = new IndexRequest.Builder<NoteVO>()
                .index(indexName)
                // 设置文档id
                .id(noteVO.getId() + "")
                // 设置文档
                .document(noteVO)
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

    @Override
    public Boolean updateDoc(NoteVO noteVO) {
        UpdateRequest<Note, NoteVO> request = new UpdateRequest.Builder<Note, NoteVO>()
                .id(noteVO.getId() + "")
                .index(indexName)
                .doc(noteVO)
                .build();
        try {
            UpdateResponse<Note> fileDocumentGetResponse = esClient.update(request, Note.class);
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
            rabbitTemplate.convertAndSend(NoteMqConstant.EXCHANGE_NAME, NoteMqConstant.UPDATE_KEY, note.getId());
        }
        return isSuccess;
    }

    @Override
    public Note selectById(Long id) {
        return this.baseMapper.selectById(id);
    }

    @Override
    public ElasticSearchResult<NoteVO> search(NoteSearchForm noteSearchForm) {
       return noteManager.getNoteByEs(noteSearchForm);
    }
}
