package com.example.red.book.service.impl;

import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.entity.Comment;
import com.example.red.book.entity.Note;
import com.example.red.book.mapper.CommentMapper;
import com.example.red.book.model.form.CommentAddForm;
import com.example.red.book.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.service.NoteService;
import com.example.red.book.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2022-09-19
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    NoteService noteService;

    @Autowired
    UserService userService;

    @Override
    public Boolean add(CommentAddForm commentAddForm, Long userId) {
        Note note = noteService.selectById(commentAddForm.getNoteId());
        if (note == null) {
           throw GlobalException.from("笔记不存在");
        }

        if (commentAddForm.getToUserId() != null) {
            if (userService.selectById(commentAddForm.getToUserId()) == null) {
                throw GlobalException.from("回复的用户不存在");
            }
        }
        //没有parent_id则为一级评论
        if (commentAddForm.getParentId() != null) {
            Comment parentComment = baseMapper.selectById(commentAddForm.getParentId());
            if (parentComment == null) {
                throw GlobalException.from("父评论不存在");
            }
        }
        Comment comment = new Comment();
        comment.setNoteId(commentAddForm.getNoteId());
        comment.setContent(commentAddForm.getContent());
        comment.setToUserId(commentAddForm.getToUserId());
        comment.setUserId(userId);
        return this.save(comment);
    }
}
