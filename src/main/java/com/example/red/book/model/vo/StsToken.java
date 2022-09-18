package com.example.red.book.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StsToken {
    @ApiModelProperty("sts AccessKeyID")
    private String accessKeyId;
    @ApiModelProperty("sts accessKeySecret")
    private String accessKeySecret;
    @ApiModelProperty("sts securityToken")
    private String securityToken;
    @ApiModelProperty("sts expiration")
    private String expiration;
}
