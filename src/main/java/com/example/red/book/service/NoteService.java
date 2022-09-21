package com.example.red.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.red.book.common.api.CommonPage;
import com.example.red.book.entity.Note;
import com.example.red.book.model.param.NoteAddParam;
import com.example.red.book.model.param.NoteQueryParam;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author franky
 * @since 2022-09-17
 */
public interface NoteService extends IService<Note> {

    Boolean add(NoteAddParam noteAddParam, Long userId);

    Boolean update(NoteAddParam noteAddParam, Long userId);

    CommonPage<Note> query(NoteQueryParam noteQueryParam);
}
