package com.example.red.book.common.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description = "分页查询参数对象")
public class PageQueryParam {
    @ApiModelProperty(value = "每页数量")
    private Integer size = 10;
    @ApiModelProperty(value = "当前页码")
    private Long currentPage = 1L;

}
