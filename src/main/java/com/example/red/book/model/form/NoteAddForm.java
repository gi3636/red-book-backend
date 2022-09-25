package com.example.red.book.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
public class NoteAddForm {

    @NotBlank(message = "笔记标题不能为空")
    @ApiModelProperty(value = "笔记标题", required = true)
    private String title;

    @NotBlank(message = "笔记内容不能为空")
    @ApiModelProperty(value = "笔记内容", required = true)
    private String content;

    @NotNull(message = "笔记图片不能为空")
    @ApiModelProperty(value = "笔记图片", required = true)
    private List<String> images;

    @NotNull(message = "笔记是否公开不能为空")
    @ApiModelProperty(value = "是否公开", required = true)
    private Boolean isPublic;

}
