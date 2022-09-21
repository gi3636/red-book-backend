package com.example.red.book.model.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.example.red.book.entity.Note;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class NoteVO {

    @ApiModelProperty("笔记Id")
    private Long id;

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("笔记标题")
    private String title;

    @ApiModelProperty("笔记内容")
    private String content;

    @ApiModelProperty("笔记收藏数")
    private Integer followCount;

    @ApiModelProperty("笔记点赞数")
    private Integer likeCount;

    @ApiModelProperty("浏览数")
    private Integer viewCount;

    @ApiModelProperty("笔记图片")
    private List<String> images;

    @ApiModelProperty("是否公开")
    private Boolean isPublic;

    @ApiModelProperty("创建时间;创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    public static List<NoteVO> convert(List<Note> records) {
        return records.stream().map(note -> {
            NoteVO noteVO = new NoteVO();
            noteVO.setId(note.getId());
            noteVO.setUserId(note.getUserId());
            noteVO.setTitle(note.getTitle());
            noteVO.setContent(note.getContent());
            noteVO.setFollowCount(note.getFollowCount());
            noteVO.setLikeCount(note.getLikeCount());
            noteVO.setViewCount(note.getViewCount());
            noteVO.setImages(note.getImages() == null ? new ArrayList<>() : Arrays.asList((note.getImages().split(","))));
            noteVO.setIsPublic(note.getIsPublic());
            noteVO.setCreatedTime(note.getCreatedTime());
            return noteVO;
        }).collect(Collectors.toList());
    }
}