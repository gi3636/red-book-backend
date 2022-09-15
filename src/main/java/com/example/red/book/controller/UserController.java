package com.example.red.book.controller;


import com.example.red.book.common.api.CommonResult;
import com.example.red.book.model.form.UserUpdateForm;
import com.example.red.book.service.UserService;
import com.example.red.book.util.SessionUtil;
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


    @ApiOperation(value = "用户资料更新")
    @PostMapping("update")
    //TODO-Form入参用notblank之类的注解防止用户传参错误，以及手机号的正则表达式之类的，都要校验
    //TODO-方法名称如果在usercontoller里面 是不是可以直接叫update？ 这个你自己考虑比如你新建了一张表叫goods，你不会商品名称叫goods_name吧？
    public CommonResult<Boolean> update(@Validated @RequestBody UserUpdateForm userUpdateForms) {
        //TODO-写一个工具类来获取当前登录人的用户信息，获取不到就抛出异常
        //TODO-这里有一个问题就是id字段前端不用传的，所以在form里面定义是不是一个合适的方式，我认为是不合适的。是不是应该去掉id字段，然后userService
        //新增入参，或者这个id直接在service层获取，或者方法加一个字段叫id，这里主要是告诉你入参混用会导致接口混乱，处理方式有很多种，各有各的好处跟麻烦的地方。
        boolean isSuccess = userService.updateUserById(userUpdateForms);
        if (!isSuccess) {
            return CommonResult.failed("修改失败");
        }
        return CommonResult.success(null);
    }

}

