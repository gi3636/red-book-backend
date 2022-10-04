package com.example.red.book.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 评论点赞数表
 * </p>
 *
 * @author franky
 * @since 2022-09-19
 */
@Getter
@Setter
@TableName("tbl_user_comment_like")
@ApiModel(value = "UserCommentLike对象", description = "评论点赞数表")
public class UserCommentLike implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("评论id")
    private Long commentId;

    @ApiModelProperty("笔记id")
    private Long noteId;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;


}
