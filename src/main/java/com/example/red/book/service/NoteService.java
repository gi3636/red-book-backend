package com.example.red.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.red.book.entity.Note;
import com.example.red.book.model.form.NoteAddForm;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author franky
 * @since 2022-09-17
 */
public interface NoteService extends IService<Note> {

    Boolean add(NoteAddForm noteAddForm, Long userId);

    Boolean update(NoteAddForm noteAddForm, Long userId);
}
