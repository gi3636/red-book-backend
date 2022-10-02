package com.example.red.book.model.form;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class CommentAddForm {

    @NotNull(message = "笔记Id不能为空")
    @ApiModelProperty("笔记id")
    private Long noteId;

    @ApiModelProperty("回复的评论id")
    private Long parentId;

    @ApiModelProperty("回复的对象id")
    private Long toUserId;

    @NotBlank(message = "评论内容不能为空")
    @ApiModelProperty("评论内容")
    private String content;

}
