package com.example.red.book.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.entity.Note;
import com.example.red.book.mapper.NoteMapper;
import com.example.red.book.model.form.NoteQueryForm;
import org.springframework.stereotype.Component;


@Component
public class NoteManager extends ServiceImpl<NoteMapper, Note> {

    public Note getNote(Long userId, String title) {
        return baseMapper.selectOne(new LambdaQueryWrapper<Note>()
                .eq(Note::getUserId, userId)
                .eq(Note::getTitle, title));
    }


    public Page<Note> getNotePage(NoteQueryForm noteQueryForm) {
        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<Note>()
                .eq(Note::getIsPublic, noteQueryForm.getIsPublic());
        if (noteQueryForm.getUserId() != null) {
            wrapper.eq(Note::getUserId, noteQueryForm.getUserId());
        }
        Page<Note> page = new Page<>(noteQueryForm.getCurrentPage(), noteQueryForm.getSize());
        return this.baseMapper.selectPage(page, wrapper);
    }
}
