package com.example.red.book.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.red.book.entity.Note;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.red.book.model.form.NoteQueryForm;
import com.example.red.book.model.vo.NoteVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 笔记表 Mapper 接口
 * </p>
 *
 * @author franky
 * @since 2022-09-19
 */
public interface NoteMapper extends BaseMapper<Note> {

    Page<NoteVO> selectNoteList(Page<NoteVO> page, @Param("noteQueryForm") NoteQueryForm noteQueryForm);
}
