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
@TableName("tbl_note_image")
@ApiModel(value = "NoteImage对象", description = "")
public class NoteImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("笔记id")
    private Long noteId;

    @ApiModelProperty("图片url")
    private String imageUrl;


}
