package com.example.red.book.controller;


import com.example.red.book.common.api.CommonResult;
import com.example.red.book.model.form.NoteAddForm;
import com.example.red.book.service.NoteService;
import com.example.red.book.util.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Api(tags = "笔记模块")
@RestController
@RequestMapping("/api/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private SessionUtil sessionUtil;

    @ApiOperation(value = "添加笔记")
    @PostMapping("add")
    public CommonResult<ObjectUtils.Null> add(@Validated @RequestBody NoteAddForm noteAddForm) {
        Boolean isSuccess = noteService.add(noteAddForm, sessionUtil.getUserId());
        if (!isSuccess) {
            return CommonResult.failed("添加失败");
        }
        return CommonResult.success(null);
    }
}

