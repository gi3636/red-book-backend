package com.example.red.book.model.doc;


import com.example.red.book.constant.NoteConstant;
import com.example.red.book.entity.Note;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = NoteConstant.INDEX)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDoc {

    @Field(type = FieldType.Long)
    @ApiModelProperty("笔记Id")
    private Long id;

    @Field(type = FieldType.Integer)
    @ApiModelProperty("用户Id")
    private Long userId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty("笔记标题")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @ApiModelProperty("笔记内容")
    private String content;

    @Field(type = FieldType.Integer)
    @ApiModelProperty("笔记收藏数")
    private Integer followCount;

    @Field(type = FieldType.Integer)
    @ApiModelProperty("笔记点赞数")
    private Integer likeCount;

    @Field(type = FieldType.Integer)
    @ApiModelProperty("浏览数")
    private Integer viewCount;

    @Field(type = FieldType.Text)
    @ApiModelProperty("笔记图片,多个图片用逗号分隔")
    private String images;

    @Field(type = FieldType.Boolean)
    @ApiModelProperty("是否公开")
    private Boolean isPublic;
    @Field(type = FieldType.Date)
    @ApiModelProperty("创建时间;创建时间")
    private Date createdTime;

    @Field(type = FieldType.Date)
    @ApiModelProperty("更新时间;更新时间")
    private Date updatedTime;


    public NoteDoc(Note note) {
        this.id = note.getId();
        this.userId = note.getUserId();
        this.title = note.getTitle();
        this.content = note.getContent();
        this.followCount = note.getFollowCount();
        this.likeCount = note.getLikeCount();
        this.viewCount = note.getViewCount();
        this.images = note.getImages();
        this.isPublic = note.getIsPublic();
        this.createdTime = note.getCreatedTime();
        this.updatedTime = note.getUpdatedTime();
    }
}
