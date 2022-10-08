package com.example.red.book.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.red.book.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.red.book.model.form.CommentQueryForm;
import com.example.red.book.model.vo.CommentVO;
import org.apache.ibatis.annotations.Param;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * <p>
 * 评论表 Mapper 接口
 * </p>
 *
 * @author franky
 * @since 2022-09-19
 */
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 获取一级评论
     *
     * @param page
     * @param commentQueryForm
     * @return
     */
    Page<CommentVO> selectTopCommentList(Page<CommentVO> page, @Param("commentQueryForm") CommentQueryForm commentQueryForm);

    /**
     * 获取子评论
     *
     * @param parentId
     * @return
     */
    List<CommentVO> selectCommentChildrenList(Long parentId);
}
