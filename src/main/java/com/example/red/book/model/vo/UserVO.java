package com.example.red.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    @ApiModelProperty("用户id")
    private Integer id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("昵称;媒体号")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("性别;1:男  0:女  2:保密")
    private Integer sex;

    @ApiModelProperty("生日")
    private String birthday;

    @ApiModelProperty("国家")
    private String country;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("简介")
    private String description;

    @ApiModelProperty("个人介绍的背景图")
    private String cover;

    @ApiModelProperty("JwtToken")
    private String token;


}
