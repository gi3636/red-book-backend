package com.example.red.book.admin.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoteUpdateForm {

    @ApiModelProperty("笔记Id")
    private Long id;

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("笔记标题")
    private String title;

    @ApiModelProperty("笔记内容")
    private String content;

    @ApiModelProperty("笔记图片,多个图片用逗号分隔")
    private String images;

    @ApiModelProperty("是否公开")
    private Boolean isPublic;

    @ApiModelProperty("审核状态 0是未审核 1是审核通过 2是审核不通过")
    private Integer status;

}
