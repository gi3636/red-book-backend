package com.example.red.book.manager;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.common.service.RedisService;
import com.example.red.book.constant.RedisConstant;
import com.example.red.book.entity.User;
import com.example.red.book.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author lintiancheng
 */
@Component
public class UserManager extends ServiceImpl<UserMapper, User> {

    private final String userKey = RedisConstant.REDIS_DATABASE + ":" + RedisConstant.REDIS_KEY_USER + ":";

    @Autowired
    private RedisService redisService;

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    public User getAndSetCacheByUsername(String username) {
        if (username == null) {
            throw GlobalException.from("username 不能为空");
        }
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User existUser = baseMapper.selectOne(wrapper);
        if (existUser != null) {
            String key = userKey + existUser.getUsername();
            redisService.set(key, existUser, RedisConstant.REDIS_EXPIRE);
        }
        return existUser;
    }


    public User getUserByCache(String username) {
        if (username == null) {
            throw GlobalException.from("username 不能为空");
        }
        String key = userKey + username;
        User user = (User) redisService.get(key);
        if (user != null) {
            return user;
        }
        user = this.getAndSetCacheByUsername(username);
        return user;
    }

}
