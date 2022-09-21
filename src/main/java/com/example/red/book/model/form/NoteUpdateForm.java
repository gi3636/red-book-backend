package com.example.red.book.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class NoteUpdateForm {

    @NotBlank(message = "笔记id不能为空")
    @ApiModelProperty("笔记Id")
    private Long id;

    @ApiModelProperty("笔记标题")
    private String title;

    @ApiModelProperty("笔记内容")
    private String content;

    @ApiModelProperty("笔记图片,多个图片用逗号分隔")
    private String images;

    @ApiModelProperty("是否公开 1是公开，0是个人可见")
    private Integer isPublic;

}
