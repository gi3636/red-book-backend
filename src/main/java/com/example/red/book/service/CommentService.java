package com.example.red.book.service;

import com.example.red.book.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.red.book.model.form.CommentAddForm;

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
}
