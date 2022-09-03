package com.example.red.book.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.entity.User;
import com.example.red.book.mapper.UserMapper;
import org.springframework.stereotype.Component;

/**
 * @author lintiancheng
 */
@Component
public class UserManager extends ServiceImpl<UserMapper, User> {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    public User getByUsername(String username) {
        if (username == null) {
            throw GlobalException.from("username cannot be null");
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        return baseMapper.selectOne(wrapper);
    }

}
