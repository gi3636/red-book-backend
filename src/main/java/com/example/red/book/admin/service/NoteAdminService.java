package com.example.red.book.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.red.book.admin.model.form.NoteQueryForm;
import com.example.red.book.admin.model.form.NoteUpdateForm;
import com.example.red.book.common.api.CommonPage;
import com.example.red.book.common.api.ElasticSearchResult;
import com.example.red.book.entity.Note;
import com.example.red.book.model.doc.NoteDoc;
import com.example.red.book.model.form.NoteSearchForm;
import com.example.red.book.model.vo.NoteVO;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author franky
 * @since 2022-09-17
 */
public interface NoteAdminService extends IService<Note> {

    Boolean updateEsDoc(NoteDoc noteDoc);

    Boolean update(NoteUpdateForm note);

    Note selectById(Long id);

    ElasticSearchResult<NoteDoc> search(NoteSearchForm noteSearchForm);

    CommonPage<NoteVO> query(NoteQueryForm noteQueryForm);
}
