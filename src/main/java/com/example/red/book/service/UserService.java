package com.example.red.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.red.book.entity.User;
import com.example.red.book.model.param.RegisterParam;
import com.example.red.book.model.param.UserUpdateParam;
import com.example.red.book.model.vo.UserVO;
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

    UserVO login(String username, String password);

    Boolean register(RegisterParam registerParam);

    Boolean updateUserById(UserUpdateParam userVO);
}
