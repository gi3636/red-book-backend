package com.example.red.book.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.entity.Comment;
import com.example.red.book.mapper.CommentMapper;
import com.example.red.book.service.CommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author franky
 * @since 2022-09-17
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {


}
