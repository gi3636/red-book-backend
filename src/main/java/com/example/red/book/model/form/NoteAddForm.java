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

    @Range(min = 0, max = 1, message = "只能是0或1")
    @NotNull(message = "笔记是否公开不能为空")
    @ApiModelProperty(value = "是否公开 1是公开，0是个人可见", required = true)
    private Integer isPublic;

}
