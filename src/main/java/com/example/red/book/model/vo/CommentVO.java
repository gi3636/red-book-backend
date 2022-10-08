package com.example.red.book.model.vo;

import com.example.red.book.entity.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class CommentVO {
    @ApiModelProperty("评论id")
    private Long id;

    @ApiModelProperty("笔记id")
    private Long noteId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("回复的评论id")
    private Long parentId;

    @ApiModelProperty("回复的对象id")
    private Long toUserId;

    @ApiModelProperty("回复的对象昵称")
    private String toUserNickname;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论点赞数")
    private Integer likeCount;

    @ApiModelProperty("创建时间;创建时间")
    private Date createdTime;

    @ApiModelProperty("子评论")
    private List<CommentVO> children = new ArrayList<>();

    public static List<CommentVO> convert(List<Comment> comments) {
        List<CommentVO> commentVOList = new ArrayList<>();
        for (Comment comment : comments) {
            CommentVO commentVO = new CommentVO();
            commentVO.setId(comment.getId());
            commentVO.setNoteId(comment.getNoteId());
            commentVO.setUserId(comment.getUserId());
            commentVO.setParentId(comment.getParentId());
            commentVO.setToUserId(comment.getToUserId());
            commentVO.setContent(comment.getContent());
            commentVO.setLikeCount(comment.getLikeCount());
            commentVO.setCreatedTime(comment.getCreatedTime());
            commentVOList.add(commentVO);
        }
        return commentVOList;
    }
}
