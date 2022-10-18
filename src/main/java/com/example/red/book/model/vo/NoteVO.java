package com.example.red.book.model.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.example.red.book.entity.Note;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoteVO {

    @ApiModelProperty("笔记Id")
    private Long id;

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("用户头像")
    private String avatar;

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

    @ApiModelProperty("用于获取数据")
    private String images;

    @ApiModelProperty("笔记图片")
    private List<String> imageList;

    @ApiModelProperty("是否公开")
    private Boolean isPublic;

    @ApiModelProperty("是否点赞")
    private Boolean isLike;

    @ApiModelProperty("审核状态 0是未审核 1是审核通过 2是审核不通过")
    private Integer status;

    @ApiModelProperty("创建时间;创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    public static List<NoteVO> convert(List<Note> records) {
        return records.stream().map(NoteVO::getNoteVO).collect(Collectors.toList());
    }

    public static NoteVO convert(Note note) {
        return getNoteVO(note);
    }

    private static NoteVO getNoteVO(Note note) {
        NoteVO noteVO = new NoteVO();
        noteVO.setId(note.getId());
        noteVO.setUserId(note.getUserId());
        noteVO.setTitle(note.getTitle());
        noteVO.setContent(note.getContent());
        noteVO.setFavoriteCount(note.getFavoriteCount());
        noteVO.setLikeCount(note.getLikeCount());
        noteVO.setViewCount(note.getViewCount());
        noteVO.setImageList(note.getImages() == null ? new ArrayList<>() : Arrays.asList((note.getImages().split(","))));
        noteVO.setIsPublic(note.getIsPublic());
        noteVO.setCreatedTime(note.getCreatedTime());
        return noteVO;
    }
}