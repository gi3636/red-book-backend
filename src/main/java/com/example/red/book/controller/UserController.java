package com.example.red.book.controller;


import com.example.red.book.common.api.CommonResult;
import com.example.red.book.entity.User;
import com.example.red.book.model.form.LoginForm;
import com.example.red.book.model.vo.UserVO;
import com.example.red.book.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2022-08-29
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("update")
    public CommonResult<Boolean> updateUser(@Validated @RequestBody UserVO userVO){
        User user = new User();
        BeanUtils.copyProperties(userVO,user);
        boolean isSuccess = userService.updateById(user);
        return CommonResult.success(isSuccess);
    }
   
}

