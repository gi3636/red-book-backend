package com.example.red.book.model.vo;

import lombok.Data;

@Data
public class LikeCountVO {

    private Integer likeCount;

    private Long noteId;

    public LikeCountVO(Long noteId, Integer value) {
        this.noteId = noteId;
        this.likeCount = value;
    }
}
