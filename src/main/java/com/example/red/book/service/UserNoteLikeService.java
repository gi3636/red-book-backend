package com.example.red.book.service;

import com.example.red.book.entity.UserNoteLike;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.red.book.model.vo.LikeCountVO;

import java.util.List;

/**
 * <p>
 * 笔记点赞数表 服务类
 * </p>
 *
 * @author franky
 * @since 2022-09-19
 */
public interface UserNoteLikeService extends IService<UserNoteLike> {

    Boolean like(Long noteId, Long userId);

    Boolean unlike(Long noteId, Long userId);
    
    List<UserNoteLike> getLikedDataFromRedis();

    List<LikeCountVO> getLikedCountFromRedis();

    /**
     * 将Redis里的点赞数据存入数据库中
     */
    void transLikedFromRedis2DB();

    /**
     * 将Redis中的点赞数量数据存入数据库
     */
    void transLikedCountFromRedis2DB();

    Boolean increaseLikeCount(Long noteId);

    Boolean decreaseLikeCount(Long noteId);

}
