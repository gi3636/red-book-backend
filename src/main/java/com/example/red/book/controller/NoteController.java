package com.example.red.book.controller;


import com.example.red.book.common.api.CommonPage;
import com.example.red.book.common.api.CommonResult;
import com.example.red.book.common.api.ElasticSearchResult;
import com.example.red.book.entity.Note;
import com.example.red.book.model.doc.NoteDoc;
import com.example.red.book.model.form.NoteAddForm;
import com.example.red.book.model.form.NoteQueryForm;
import com.example.red.book.model.form.NoteSearchForm;
import com.example.red.book.model.form.NoteUpdateForm;
import com.example.red.book.model.vo.NoteVO;
import com.example.red.book.service.NoteService;
import com.example.red.book.util.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public CommonResult<Void> add(@Validated @RequestBody NoteAddForm noteAddForm) {
        Boolean isSuccess = noteService.add(noteAddForm, sessionUtil.getUserId());
        if (!isSuccess) {
            return CommonResult.failed("添加失败");
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "查询笔记")
    @PostMapping("list")
    public CommonResult<CommonPage<NoteVO>> list(@Validated @RequestBody NoteQueryForm noteQueryForm) {
        CommonPage<NoteVO> noteList = noteService.query(noteQueryForm);
        return CommonResult.success(noteList);
    }

    @ApiOperation(value = "修改笔记")
    @PostMapping("update")
    public CommonResult<CommonPage<Note>> update(@Validated @RequestBody NoteUpdateForm noteUpdateForm) {
        Boolean isSuccess = noteService.update(noteUpdateForm, sessionUtil.getUserId());
        if (!isSuccess) {
            return CommonResult.failed("修改失败");
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "搜索笔记")
    @PostMapping("search")
    public CommonResult<ElasticSearchResult<NoteDoc>> search(@Validated @RequestBody NoteSearchForm noteSearchForm) {
        ElasticSearchResult<NoteDoc> result = noteService.search(noteSearchForm);
        return CommonResult.success(result);
    }

}

