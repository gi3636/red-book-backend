package com.example.red.book.service.impl;

import com.example.red.book.entity.Comment;
import com.example.red.book.mapper.CommentMapper;
import com.example.red.book.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
