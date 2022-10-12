package com.example.red.book.admin.controller;

import com.example.red.book.admin.model.form.NoteQueryForm;
import com.example.red.book.admin.service.NoteAdminService;
import com.example.red.book.common.api.CommonPage;
import com.example.red.book.common.api.CommonResult;
import com.example.red.book.common.api.ElasticSearchResult;
import com.example.red.book.entity.Note;
import com.example.red.book.model.doc.NoteDoc;
import com.example.red.book.model.form.NoteSearchForm;
import com.example.red.book.model.form.NoteUpdateForm;
import com.example.red.book.model.vo.NoteVO;
import com.example.red.book.util.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "笔记管理模块")
@RestController
@RequestMapping("/admin/note")
public class NoteAdminController {

    @Autowired
    private NoteAdminService noteAdminService;


    @Autowired
    private SessionUtil sessionUtil;


    @ApiOperation(value = "查询笔记")
    @PostMapping("list")
    public CommonResult<CommonPage<NoteVO>> list(@Validated @RequestBody NoteQueryForm noteQueryForm) {
        CommonPage<NoteVO> noteList = noteAdminService.query(noteQueryForm);
        return CommonResult.success(noteList);
    }

    @ApiOperation(value = "修改笔记")
    @PostMapping("update")
    public CommonResult<CommonPage<Note>> update(@Validated @RequestBody NoteUpdateForm noteUpdateForm) {
        Boolean isSuccess = noteAdminService.update(noteUpdateForm, sessionUtil.getUserId());
        if (!isSuccess) {
            return CommonResult.failed("修改失败");
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "搜索笔记")
    @PostMapping("search")
    public CommonResult<ElasticSearchResult<NoteDoc>> search(@Validated @RequestBody NoteSearchForm noteSearchForm) {
        ElasticSearchResult<NoteDoc> result = noteAdminService.search(noteSearchForm);
        return CommonResult.success(result);
    }

}

