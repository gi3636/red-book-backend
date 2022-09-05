package com.example.red.book.manager;

import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.common.service.RedisService;
import com.example.red.book.constant.RedisConstant;
import com.example.red.book.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisManager {
    private final String userKey = RedisConstant.REDIS_DATABASE + ":" + RedisConstant.REDIS_KEY_USER + ":";
    @Autowired
    private RedisService redisService;


    public User getUserByUsername(String username) {
        if (username == null) {
            throw GlobalException.from("username 不能为空");
        }
        String key = userKey + username;
        return (User) redisService.get(key);
    }


    public void saveUser(User user) {
        if (user == null) {
            throw GlobalException.from("user 不能为空");
        }
        String key = userKey + user.getUsername();
        redisService.set(key, user, RedisConstant.REDIS_EXPIRE);
    }
}
