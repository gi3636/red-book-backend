package com.example.red.book.controller;


import com.example.red.book.common.api.CommonResult;
import com.example.red.book.entity.User;
import com.example.red.book.model.form.LoginForm;
import com.example.red.book.model.form.RegisterForm;
import com.example.red.book.model.vo.UserVo;
import com.example.red.book.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    UserService userService;

    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public CommonResult<UserVo> login(@RequestBody LoginForm loginForm) {
        UserVo userVo = userService.login(loginForm.getUsername(), loginForm.getPassword());
        return CommonResult.success(userVo);
    }

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<ObjectUtils.Null> register(@RequestBody RegisterForm registerForm) {
        User user = userService.register(registerForm);
        if (user == null) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }

}

