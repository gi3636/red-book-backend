package com.example.red.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.common.api.CommonPage;
import com.example.red.book.entity.Note;
import com.example.red.book.mapper.NoteMapper;
import com.example.red.book.model.form.NoteAddForm;
import com.example.red.book.model.form.NoteQueryForm;
import com.example.red.book.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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


    @Override
    public Boolean add(NoteAddForm noteAddForm, Long userId) {
        Note note = new Note();
        note.setUserId(userId);
        note.setTitle(noteAddForm.getTitle());
        note.setContent(noteAddForm.getContent());
        note.setIsPublic(noteAddForm.getIsPublic());
        note.setImages(noteAddForm.getImages());
        return this.baseMapper.insert(note) > 0;
    }

    @Override
    public Boolean update(NoteAddForm noteAddForm, Long userId) {
        Note note = new Note();
        note.setUserId(userId);
        note.setTitle(noteAddForm.getTitle());
        note.setContent(noteAddForm.getContent());
        note.setIsPublic(noteAddForm.getIsPublic());
        note.setImages(noteAddForm.getImages());
        return this.baseMapper.insert(note) > 0;
    }

    @Override
    public CommonPage<Note> query(NoteQueryForm noteQueryForm) {
        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<Note>()
                .eq(Note::getIsPublic, noteQueryForm.getIsPublic());

        if (noteQueryForm.getUserId() != null) {
            wrapper.eq(Note::getUserId, noteQueryForm.getUserId());
        }
        Page<Note> page = new Page<>(noteQueryForm.getCurrentPage(), noteQueryForm.getSize());
        Page<Note> data = this.baseMapper.selectPage(page, wrapper);
        return CommonPage.restPage(data);
    }
}
