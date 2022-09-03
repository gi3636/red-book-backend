package com.example.red.book.model.form;

import com.example.red.book.common.valid.AddGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description:注册表单
 * @author: fenggi123
 * @create: 8/13/2021 11:58 AM
 */
@Data
public class RegisterForm {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    @NotBlank(message = "确认密码不能为空")
    @ApiModelProperty(value = "确认密码")
    private String confirmPassword;
}
