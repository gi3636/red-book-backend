package com.example.red.book.admin.model.form;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.example.red.book.common.api.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ApiModel(description = "笔记查询参数对象")
public class NoteQueryForm extends PageQueryParam {

    @ApiModelProperty("笔记Id")
    private Long noteId;

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("是否公开")
    private Boolean isPublic;

    @ApiModelProperty("笔记标题")
    private String title;

    @ApiModelProperty("笔记内容")
    private String content;

    @ApiModelProperty("审核状态 0是未审核 1是审核通过 2是审核不通过")
    private Integer status;

    @ApiModelProperty("创建时间;创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @ApiModelProperty("更新时间;更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;
}
