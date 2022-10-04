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


    List<UserNoteLike> getLikeDataFromRedis();

    List<LikeCountVO> getLikedCountFromRedis();
}
