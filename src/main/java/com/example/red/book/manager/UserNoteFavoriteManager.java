package com.example.red.book.manager;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.entity.UserNoteFavorite;
import com.example.red.book.mapper.UserNoteFavoriteMapper;
import org.springframework.stereotype.Component;

@Component
public class UserNoteFavoriteManager extends ServiceImpl<UserNoteFavoriteMapper, UserNoteFavorite> {

    public UserNoteFavorite getFavoriteByUserIdAndNoteId(long userId, long noteId) {
        LambdaQueryWrapper<UserNoteFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserNoteFavorite::getUserId, userId)
                .eq(UserNoteFavorite::getNoteId, noteId);
        return baseMapper.selectOne(wrapper);
    }
}
