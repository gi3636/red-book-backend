package com.example.red.book.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.admin.service.UserCommentLikeService;
import com.example.red.book.entity.UserCommentLike;
import com.example.red.book.mapper.UserCommentLikeMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论点赞数表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2022-09-19
 */
@Service
public class UserCommentLikeServiceImpl extends ServiceImpl<UserCommentLikeMapper, UserCommentLike> implements UserCommentLikeService {

}
