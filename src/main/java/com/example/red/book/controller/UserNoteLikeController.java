package com.example.red.book.controller;


import com.example.red.book.service.UserNoteLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author franky
 * @since 2022-09-17
 */
@RestController
@RequestMapping("/api/userNoteLike")
public class UserNoteLikeController {
    @Autowired
    UserNoteLikeService userNoteLikeService;

    @GetMapping("/test")
    public void test() {
        userNoteLikeService.transLikedFromRedis2DB();
        userNoteLikeService.transLikedCountFromRedis2DB();
    }


}

