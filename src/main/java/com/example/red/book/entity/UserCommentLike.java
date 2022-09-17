package com.example.red.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author franky
 * @since 2022-09-17
 */
@Getter
@Setter
@TableName("tbl_user_comment_like")
@ApiModel(value = "UserCommentLike对象", description = "")
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


}
