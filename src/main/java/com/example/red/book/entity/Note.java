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
import lombok.Data;

/**
 * <p>
 * 笔记表
 * </p>
 *
 * @author franky
 * @since 2022-09-19
 */

@Data
@TableName("tbl_note")
@ApiModel(value = "Note对象", description = "笔记表")
public class Note implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("笔记Id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("笔记标题")
    private String title;

    @ApiModelProperty("笔记内容")
    private String content;

    @ApiModelProperty("笔记收藏数")
    private Integer favoriteCount;

    @ApiModelProperty("笔记点赞数")
    private Integer likeCount;

    @ApiModelProperty("浏览数")
    private Integer viewCount;

    @ApiModelProperty("笔记图片,多个图片用逗号分隔")
    private String images;

    @ApiModelProperty("是否公开")
    private Boolean isPublic;

    @ApiModelProperty("审核状态 0是未审核 1是审核通过 2是审核不通过")
    private Integer status;

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
