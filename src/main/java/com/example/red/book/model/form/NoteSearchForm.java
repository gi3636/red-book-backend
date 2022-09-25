package com.example.red.book.model.form;

import com.example.red.book.common.api.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "笔记查询参数对象")
public class NoteSearchForm extends PageQueryParam {

    @NotBlank(message = "搜索内容不能为空")
    @ApiModelProperty("搜索内容")
    private String keyword;
}
