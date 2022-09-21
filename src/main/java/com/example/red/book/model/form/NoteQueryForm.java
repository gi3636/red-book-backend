package com.example.red.book.model.form;

import com.example.red.book.common.api.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "笔记查询参数对象")
public class NoteQueryForm extends PageQueryParam {

    @ApiModelProperty("用户Id")
    private Long userId;

    @NotNull(message = "笔记是否公开不能为空")
    @ApiModelProperty("是否公开")
    private Boolean isPublic;
}
