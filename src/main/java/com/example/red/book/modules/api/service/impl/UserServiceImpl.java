package com.example.red.book.modules.api.service.impl;

import com.example.red.book.modules.api.model.User;
import com.example.red.book.modules.api.mapper.UserMapper;
import com.example.red.book.modules.api.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
