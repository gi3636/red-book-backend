package com.example.red.book.model.form;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class UserUpdateForm {

    //@NotNull(message = "手机号不能为空")
    @Pattern(regexp = "(\\+\\d+)?1[3458]\\d{9}$", message = "手机号格式不正确")
    @ApiModelProperty("手机号")
    private String mobile;

    @Size(min = 2, max = 11, message = "长度必须大于等于2或小于等于11")
    @ApiModelProperty("昵称;昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @Min(value = 0, message = "最小值为0")
    @Max(value = 2, message = "最大值为2")
    @ApiModelProperty("性别 0是保密 1是男 2是女")
    private Integer sex;

    @Past(message = "必须为过去的时间")
    @ApiModelProperty("生日")
    private Date birthday;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("城市")
    private String city;

    @Size(max = 100, message = "长度必须大于等于0或小于等于100")
    @ApiModelProperty("简介")
    private String description;

    @ApiModelProperty("个人介绍的背景图")
    private String cover;
}
