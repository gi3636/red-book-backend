package com.example.red.book.controller;


import com.example.red.book.common.api.CommonPage;
import com.example.red.book.common.api.CommonResult;
import com.example.red.book.entity.Note;
import com.example.red.book.model.form.NoteAddForm;
import com.example.red.book.model.form.NoteUpdateForm;
import com.example.red.book.model.form.NoteQueryForm;
import com.example.red.book.service.NoteService;
import com.example.red.book.util.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "查询笔记")
    @PostMapping("list")
    public CommonResult<CommonPage<Note>> list(@RequestBody NoteQueryForm noteQueryForm) {
        CommonPage<Note> noteList = noteService.query(noteQueryForm);
        return CommonResult.success(noteList);
    }

    @ApiOperation(value = "修改笔记")
    @PostMapping("update")
    public CommonResult<CommonPage<Note>> update(@RequestBody NoteUpdateForm noteQueryParam) {
        return CommonResult.success(null);
    }
}

