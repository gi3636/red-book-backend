package com.example.red.book.admin.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.admin.manager.NoteAdminManager;
import com.example.red.book.admin.mapper.NoteAdminMapper;
import com.example.red.book.admin.model.form.NoteQueryForm;
import com.example.red.book.admin.model.form.NoteUpdateForm;
import com.example.red.book.admin.service.NoteAdminService;
import com.example.red.book.common.api.CommonPage;
import com.example.red.book.common.api.ElasticSearchResult;
import com.example.red.book.common.service.RedisService;
import com.example.red.book.constant.NoteConstant;
import com.example.red.book.entity.Note;
import com.example.red.book.model.doc.NoteDoc;
import com.example.red.book.model.form.NoteSearchForm;
import com.example.red.book.model.vo.NoteVO;
import com.example.red.book.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
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
public class NoteAdminServiceImpl extends ServiceImpl<NoteAdminMapper, Note> implements NoteAdminService {

    private static final String indexName = NoteConstant.INDEX;
    @Autowired
    NoteAdminManager noteAdminManager;
    @Autowired
    private ElasticsearchClient esClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

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
    public Boolean update(NoteUpdateForm noteUpdateForm) {
        Note note = new Note();
        note.setId(noteUpdateForm.getId());
        note.setUserId(noteUpdateForm.getUserId());
        note.setTitle(noteUpdateForm.getTitle());
        note.setContent(noteUpdateForm.getContent());
        note.setIsPublic(noteUpdateForm.getIsPublic());
        note.setImages(noteUpdateForm.getImages());
        Boolean isSuccess = this.baseMapper.updateById(note) > 0;
        if (isSuccess) {
            rabbitTemplate.convertAndSend(NoteConstant.EXCHANGE_NAME, NoteConstant.UPDATE_KEY, note);
        }
        return isSuccess;
    }


    public Boolean update(Note note) {
        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<Note>();
        wrapper.eq(Note::getId, note.getId())
                .eq(Note::getUserId, note.getUserId());
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
        return noteAdminManager.getNoteByEs(noteSearchForm);
    }

    @Override
    public CommonPage<NoteVO> query(NoteQueryForm noteQueryForm) {
        Page<NoteVO> data = noteAdminManager.getNoteVOPage(noteQueryForm);
        return CommonPage.restPage(data);
    }

}
