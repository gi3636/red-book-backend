package com.example.red.book.controller;


import com.example.red.book.common.api.CommonResult;
import com.example.red.book.model.form.UserUpdateForm;
import com.example.red.book.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2022-08-29
 */
@Api(tags = "用户模块")
@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    HttpServletRequest request;

    @ApiOperation(value = "用户资料更新")
    @PostMapping("update")
    public CommonResult<Boolean> updateUser(@Validated @RequestBody UserUpdateForm userUpdateForms) {
        Long id = (Long) request.getSession().getAttribute("id");
        userUpdateForms.setId(id);
        boolean isSuccess = userService.updateUserById(userUpdateForms);
        if (!isSuccess) {
            return CommonResult.failed("修改失败");
        }
        return CommonResult.success(null);
    }

}

