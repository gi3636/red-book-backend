package com.example.red.book.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.entity.Note;
import com.example.red.book.mapper.NoteMapper;
import com.example.red.book.model.form.NoteAddForm;
import com.example.red.book.service.NoteImageService;
import com.example.red.book.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author franky
 * @since 2022-09-17
 */
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

    @Autowired
    NoteImageService noteImageService;

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
}
