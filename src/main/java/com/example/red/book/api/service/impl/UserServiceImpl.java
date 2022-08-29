package com.example.red.book.api.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.api.mapper.UserMapper;
import com.example.red.book.api.model.User;
import com.example.red.book.api.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2022-08-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
