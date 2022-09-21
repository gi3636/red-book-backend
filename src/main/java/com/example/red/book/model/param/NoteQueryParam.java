package com.example.red.book.model.param;

import com.example.red.book.common.api.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "笔记查询参数对象")
public class NoteQueryParam extends PageQueryParam {

    @ApiModelProperty("用户Id")
    private Long userId;

    @Range(min = 0, max = 1, message = "只能是0或1")
    @NotNull(message = "笔记是否公开不能为空")
    @ApiModelProperty("是否公开 1是公开，0是个人可见")
    private Integer isPublic;
}
