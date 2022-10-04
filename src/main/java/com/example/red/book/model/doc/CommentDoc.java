package com.example.red.book.model.doc;

import com.baomidou.mybatisplus.annotation.*;
import com.example.red.book.constant.CommentConstant;
import com.example.red.book.constant.NoteConstant;
import com.example.red.book.entity.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(indexName = CommentConstant.INDEX)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDoc {
    @Field(type = FieldType.Long)
    @ApiModelProperty("评论id")
    private Long id;

    @Field(type = FieldType.Long)
    @ApiModelProperty("笔记id")
    private Long noteId;

    @Field(type = FieldType.Long)
    @ApiModelProperty("用户id")
    private Long userId;

    @Field(type = FieldType.Long)
    @ApiModelProperty("回复的评论id")
    private Long parentId;

    @Field(type = FieldType.Long)
    @ApiModelProperty("回复的对象id")
    private Long toUserId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty("评论内容")
    private String content;

    @Field(type = FieldType.Long)
    @ApiModelProperty("评论点赞数")
    private Integer likeCount;

    @Field(type = FieldType.Date)
    @ApiModelProperty("创建时间;创建时间")
    private Date createdTime;

    @ApiModelProperty("子评论")
    List<CommentDoc> children = new ArrayList<>();

    public CommentDoc(Comment comment) {
        this.id = comment.getId();
        this.noteId = comment.getNoteId();
        this.userId = comment.getUserId();
        this.parentId = comment.getParentId();
        this.toUserId = comment.getToUserId();
        this.content = comment.getContent();
        this.likeCount = comment.getLikeCount();
        this.createdTime = comment.getCreatedTime();
    }
}
