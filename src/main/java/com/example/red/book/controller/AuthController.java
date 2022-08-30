package com.example.red.book.controller;


import com.example.red.book.entity.User;
import com.example.red.book.common.api.CommonResult;
import com.example.red.book.model.form.LoginForm;
import com.example.red.book.model.form.RegisterForm;
import com.example.red.book.modules.ums.dto.UmsAdminParam;
import com.example.red.book.modules.ums.model.UmsAdmin;
import com.example.red.book.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    UserService userService;

    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public CommonResult login(@RequestBody LoginForm loginForm) {
        String token = userService.login(loginForm.getUsername(), loginForm.getPassword());
        if (token == null) {
            return CommonResult.validateFailed("用户名或密码错误");
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("users", "");
        return CommonResult.success(tokenMap);
    }

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<UmsAdmin> register(@RequestBody RegisterForm registerForm) {
        User user = userService.register(registerForm);
        if (user == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }

}

