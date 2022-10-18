package com.example.red.book.service;

import com.example.red.book.entity.UserNoteFavorite;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 笔记收藏数表 服务类
 * </p>
 *
 * @author franky
 * @since 2022-09-19
 */
public interface UserNoteFavoriteService extends IService<UserNoteFavorite> {

    Boolean favorite(Long noteId, Long userId);

    Boolean cancelFavorite(Long noteId, Long userId);

    /**
     * 将Redis里的点赞数据存入数据库中
     */
    void transFavoriteFromRedis2DB();

    /**
     * 将Redis中的点赞数量数据存入数据库
     */
    void transFavoriteCountFromRedis2DB();

}
