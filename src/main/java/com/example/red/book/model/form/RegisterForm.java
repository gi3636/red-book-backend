package com.example.red.book.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:注册表单
 * @author: fenggi123
 * @create: 8/13/2021 11:58 AM
 */
@Data
public class RegisterForm {
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "确认密码")
    private String confirmPassword;
}
