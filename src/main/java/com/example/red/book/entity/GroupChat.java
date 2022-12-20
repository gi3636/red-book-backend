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
 * 群组表
 * </p>
 *
 * @author franky
 * @since 2022-12-19
 */
@Getter
@Setter
@TableName("tbl_group_chat")
@ApiModel(value = "GroupChat对象", description = "群组表")
public class GroupChat implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("群组ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("群主ID")
    private Long adminId;

    @ApiModelProperty("群名称")
    private String groupName;

    @ApiModelProperty("群头像")
    private String groupAvatar;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    @ApiModelProperty("是否删除;1是删除，0是不删除")
    @TableLogic
    private Boolean deleted;


}
