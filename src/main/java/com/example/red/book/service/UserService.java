package com.example.red.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.red.book.entity.User;
import com.example.red.book.model.form.RegisterForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author franky
 * @since 2022-08-29
 */
@Service
public interface UserService extends IService<User> {

    String login(String username, String password);

    User register(RegisterForm registerForm);
}
