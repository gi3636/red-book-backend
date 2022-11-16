package com.example.red.book.controller;


import com.example.red.book.common.api.CommonPage;
import com.example.red.book.common.api.CommonResult;
import com.example.red.book.entity.Note;
import com.example.red.book.model.form.NoteAddForm;
import com.example.red.book.model.form.NoteQueryForm;
import com.example.red.book.model.form.NoteSearchForm;
import com.example.red.book.model.form.NoteUpdateForm;
import com.example.red.book.model.vo.NoteVO;
import com.example.red.book.service.NoteService;
import com.example.red.book.service.UserNoteFavoriteService;
import com.example.red.book.service.UserNoteLikeService;
import com.example.red.book.util.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    private UserNoteLikeService userNoteLikeService;

    @Autowired
    private UserNoteFavoriteService userNoteFavoriteService;

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
        CommonPage<NoteVO> noteList = noteService.query(noteQueryForm, sessionUtil.getUserId());
        return CommonResult.success(noteList);
    }

    @ApiOperation(value = "查询点过赞的笔记")
    @PostMapping("like/list")
    public CommonResult<CommonPage<NoteVO>> getLikedList(@Validated @RequestBody NoteQueryForm noteQueryForm) {
        CommonPage<NoteVO> noteList = noteService.queryLike(noteQueryForm, sessionUtil.getUserId());
        return CommonResult.success(noteList);
    }


    @ApiOperation(value = "推荐笔记")
    @GetMapping("recommend")
    public CommonResult<CommonPage<NoteVO>> recommend() {
        CommonPage<NoteVO> noteList = noteService.queryRecommend(sessionUtil.getUserId());
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
    public CommonResult<CommonPage<NoteVO>> search(@Validated @RequestBody NoteSearchForm noteSearchForm) {
        CommonPage<NoteVO> result = noteService.search(noteSearchForm, sessionUtil.getUserId());
        return CommonResult.success(result);
    }

    @ApiOperation(value = "笔记点赞")
    @PostMapping("like/{noteId}")
    public CommonResult<Void> like(@PathVariable("noteId") Long noteId) {
        Boolean isSuccess = userNoteLikeService.like(noteId, sessionUtil.getUserId());
        if (!isSuccess) {
            return CommonResult.failed("点赞失败");
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "笔记取消点赞")
    @PostMapping("unlike/{noteId}")
    public CommonResult<Void> unlike(@PathVariable("noteId") Long noteId) {
        Boolean isSuccess = userNoteLikeService.unlike(noteId, sessionUtil.getUserId());
        if (!isSuccess) {
            return CommonResult.failed("取消点赞失败");
        }
        return CommonResult.success(null);
    }


    @ApiOperation(value = "笔记收藏")
    @PostMapping("favorite/{noteId}")
    public CommonResult<Void> favorite(@PathVariable("noteId") Long noteId) {
        Boolean isSuccess = userNoteFavoriteService.favorite(noteId, sessionUtil.getUserId());
        if (!isSuccess) {
            return CommonResult.failed("收藏失败");
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "笔记取消收藏")
    @PostMapping("cancelFavorite/{noteId}")
    public CommonResult<Void> cancelFavorite(@PathVariable("noteId") Long noteId) {
        Boolean isSuccess = userNoteFavoriteService.cancelFavorite(noteId, sessionUtil.getUserId());
        if (!isSuccess) {
            return CommonResult.failed("取消收藏失败");
        }
        return CommonResult.success(null);
    }


}

