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
 * 笔记点赞数表
 * </p>
 *
 * @author franky
 * @since 2022-09-19
 */
@Getter
@Setter
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



}
