package com.example.red.book.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimerInfo {
    private Integer totalFireCount;
    private Boolean runForever;
    private Long repeatIntervalMs;

    private Long initialOffsetMs = 0L;

    private String callbackData;
}
