package com.example.red.book.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 笔记点赞数表
 * </p>
 *
 * @author franky
 * @since 2022-09-19
 */
@Data
@NoArgsConstructor
@TableName("tbl_user_note_like")
@ApiModel(value = "UserNoteLike对象", description = "笔记点赞数表")
public class UserNoteLike implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("笔记id")
    private Long noteId;

    @ApiModelProperty("点赞状态 0是取消 1是点赞")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;


    public UserNoteLike(Long userId, Long noteId, Integer value) {
        this.userId = userId;
        this.noteId = noteId;
        this.status = value;
    }
}
