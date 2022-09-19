package com.example.red.book.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author franky
 * @since 2022-09-19
 */
@Getter
@Setter
@TableName("tbl_comment")
@ApiModel(value = "Comment对象", description = "评论表")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("评论id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("笔记id")
    private Long noteId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("父id")
    private Long parentId;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论点赞数")
    private Integer likeCount;

    @ApiModelProperty("创建时间;创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @ApiModelProperty("更新时间;更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    @ApiModelProperty("是否删除;1是删除，0是不删除")
    @TableLogic
    private Boolean deleted;


}
