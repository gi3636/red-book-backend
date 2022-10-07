package com.example.red.book.controller;


import com.example.red.book.common.api.CommonPage;
import com.example.red.book.common.api.CommonResult;
import com.example.red.book.common.api.ElasticSearchResult;
import com.example.red.book.manager.CommentManager;
import com.example.red.book.model.doc.CommentDoc;
import com.example.red.book.model.doc.NoteDoc;
import com.example.red.book.model.form.CommentAddForm;
import com.example.red.book.model.form.CommentQueryForm;
import com.example.red.book.model.vo.CommentVO;
import com.example.red.book.service.CommentService;
import com.example.red.book.util.SessionUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author franky
 * @since 2022-09-17
 */
@Api(tags = "评论模块")
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private CommentManager commentManager;

    @ApiOperation(value = "添加评论")
    @PostMapping("add")
    public CommonResult<Void> add(@Validated @RequestBody CommentAddForm commentAddForm) {
        Boolean isSuccess = commentService.add(commentAddForm, sessionUtil.getUserId());
        if (!isSuccess) {
            return CommonResult.failed("添加失败");
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "查询笔记评论")
    @PostMapping("list")
    public CommonResult<CommonPage<CommentVO>> list(@Validated @RequestBody CommentQueryForm commentQueryForm) {
        CommonPage<CommentVO> commentVOList = commentService.query(commentQueryForm, sessionUtil.getUserId());
        return CommonResult.success(commentVOList);
    }

    @ApiOperation(value = "查询评论")
    @PostMapping("es/list/")
    public CommonResult<ElasticSearchResult<CommentDoc>> query(@Validated @RequestBody CommentQueryForm commentQueryForm) {
        ElasticSearchResult<CommentDoc> result = commentService.queryByEs(commentQueryForm, sessionUtil.getUserId());
        return CommonResult.success(result);
    }


}

