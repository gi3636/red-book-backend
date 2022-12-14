package com.example.red.book.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * 笔记收藏数表
 * </p>
 *
 * @author franky
 * @since 2022-09-19
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("tbl_user_note_favorite")
@ApiModel(value = "UserNoteFavorite对象", description = "笔记收藏数表")
public class UserNoteFavorite implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("笔记id")
    private Long noteId;

    @ApiModelProperty("收藏状态 0是取消 1是收藏")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;


    public UserNoteFavorite(Long userId, Long noteId, Integer value) {
        this.userId = userId;
        this.noteId = noteId;
        this.status = value;
    }
}
