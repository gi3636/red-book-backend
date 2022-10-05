package com.example.red.book.manager;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.entity.UserNoteLike;
import com.example.red.book.mapper.UserNoteLikeMapper;
import org.springframework.stereotype.Component;

@Component
public class UserNoteLikeManager extends ServiceImpl<UserNoteLikeMapper, UserNoteLike> {

    public UserNoteLike getLikeByUserIdAndNoteId(long userId, long noteId) {
        LambdaQueryWrapper<UserNoteLike> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserNoteLike::getUserId, userId)
                .eq(UserNoteLike::getNoteId, noteId);
        return baseMapper.selectOne(wrapper);
    }
}
