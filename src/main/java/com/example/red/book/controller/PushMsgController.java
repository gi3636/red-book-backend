package com.example.red.book.controller;

import com.example.red.book.netty.bean.DataContent;
import com.example.red.book.service.impl.PushMsgServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/push")
public class PushMsgController {

    /**
     * 同时为了方便操作，我们还可以提取出
     * Controller
     */

    @Autowired
    PushMsgServiceImpl pushMsgService;

    @PostMapping("/pushOne")
    public void pushOne(@RequestBody DataContent dataContent) {
        pushMsgService.pushMsgToOne(dataContent);
    }

    @PostMapping("/pushAll")
    public void pushAll(@RequestBody DataContent dataContent) {
        pushMsgService.pushMsgToAll(dataContent);
    }
}
