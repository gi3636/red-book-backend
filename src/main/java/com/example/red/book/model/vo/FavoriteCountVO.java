package com.example.red.book.model.vo;

import lombok.Data;

@Data
public class FavoriteCountVO {

    private Integer favoriteCount;

    private Long noteId;

    public FavoriteCountVO(Long noteId, Integer value) {
        this.noteId = noteId;
        this.favoriteCount = value;
    }
}
