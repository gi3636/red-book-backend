package com.example.red.book.model.form;

import com.example.red.book.common.api.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentQueryForm  extends PageQueryParam {

    @NotNull(message ="笔记id不能为空")
    @ApiModelProperty("笔记id")
    private Long noteId;
}
