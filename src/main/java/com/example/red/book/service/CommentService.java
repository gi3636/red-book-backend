package com.example.red.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.red.book.common.api.CommonPage;
import com.example.red.book.common.api.ElasticSearchResult;
import com.example.red.book.entity.Comment;
import com.example.red.book.model.doc.CommentDoc;
import com.example.red.book.model.form.CommentAddForm;
import com.example.red.book.model.form.CommentQueryForm;
import com.example.red.book.model.vo.CommentVO;

import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author franky
 * @since 2022-09-19
 */
public interface CommentService extends IService<Comment> {

    Boolean add(CommentAddForm commentAddForm, Long userId);

    CommonPage<CommentVO> query(CommentQueryForm commentQueryForm, Long userId);

    void addEsDoc(CommentDoc commentDoc);

    ElasticSearchResult<CommentDoc> queryByEs(CommentQueryForm commentQueryForm, Long userId);
}
