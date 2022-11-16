package com.example.red.book.model.form;

import com.example.red.book.common.api.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "笔记查询参数对象")
public class NoteQueryForm extends PageQueryParam {

    @NotNull(message = "用户id不能为空")
    @ApiModelProperty("用户Id")
    private Long userId;
}
