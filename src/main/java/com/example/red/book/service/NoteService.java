package com.example.red.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.red.book.common.api.CommonPage;
import com.example.red.book.entity.Note;
import com.example.red.book.model.doc.NoteDoc;
import com.example.red.book.model.form.NoteAddForm;
import com.example.red.book.model.form.NoteQueryForm;
import com.example.red.book.model.form.NoteSearchForm;
import com.example.red.book.model.form.NoteUpdateForm;
import com.example.red.book.model.vo.NoteVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author franky
 * @since 2022-09-17
 */
public interface NoteService extends IService<Note> {

    Boolean add(NoteAddForm noteAddForm, Long userId);


    CommonPage<NoteVO> query(NoteQueryForm noteQueryForm, Long selfId);

    Boolean addEsDoc(NoteDoc noteDoc);

    Boolean updateEsDoc(NoteDoc noteDoc);

    Note selectByUserIdAndTitle(Long userId, String title);

    Boolean update(NoteUpdateForm noteUpdateForm, Long userId);

    Boolean update(Note note);

    Note selectById(Long id);

    CommonPage<NoteVO> search(NoteSearchForm noteSearchForm, Long userId);

    CommonPage<NoteVO> queryRecommend(Long selfId, Integer currentPage, Integer pageSize);

    CommonPage<NoteVO> queryLike(NoteQueryForm noteQueryForm, Long userId);

    CommonPage<NoteVO> queryFavorite(NoteQueryForm noteQueryForm, Long userId);

    NoteVO getNote(Long id);

}
