package com.example.red.book.model.vo;

import lombok.Data;

@Data
public class LikeCountVO {

    private Integer likeCount;

    private String key;

    public LikeCountVO(String key, Integer value) {
        this.key = key;
        this.likeCount = value;
    }
}
