package com.example.red.book.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class NoteAddForm {

    @NotBlank(message = "笔记标题不能为空")
    @ApiModelProperty(value = "笔记标题", required = true)
    private String title;

    @NotBlank(message = "笔记内容不能为空")
    @ApiModelProperty(value = "笔记内容", required = true)
    private String content;

    @NotBlank(message = "笔记图片不能为空")
    @ApiModelProperty(value = "笔记图片,多个图片用逗号分隔", required = true)
    private String images;

    @NotNull(message = "笔记是否公开不能为空")
    @ApiModelProperty(value = "是否公开", required = true)
    private Boolean isPublic;

}
