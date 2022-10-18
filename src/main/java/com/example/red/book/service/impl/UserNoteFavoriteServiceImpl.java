package com.example.red.book.service.impl;

import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.common.service.RedisService;
import com.example.red.book.constant.NoteConstant;
import com.example.red.book.entity.Note;
import com.example.red.book.entity.UserNoteFavorite;
import com.example.red.book.manager.UserNoteFavoriteManager;
import com.example.red.book.mapper.UserNoteFavoriteMapper;
import com.example.red.book.model.vo.FavoriteCountVO;
import com.example.red.book.service.NoteService;
import com.example.red.book.service.UserNoteFavoriteService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 笔记收藏数表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2022-09-19
 */
@Service
@Slf4j
public class UserNoteFavoriteServiceImpl extends ServiceImpl<UserNoteFavoriteMapper, UserNoteFavorite> implements UserNoteFavoriteService {

    @Autowired
    private UserNoteFavoriteManager userNoteFavoriteManager;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private NoteService noteService;
    @Autowired
    private RedisService redisService;

    public Boolean increaseFavoriteCount(Long noteId) {
        try {
            redisService.hIncr(NoteConstant.USER_NOTE_FAVORITE_KEY, noteId + "", 1L);
            return true;
        } catch (Exception e) {
            log.error("增加点赞数失败: {}", e.getMessage(), e);
            return false;
        }
    }

    public Boolean decreaseFavoriteCount(Long noteId) {
        try {
            redisService.hDecr(NoteConstant.USER_NOTE_FAVORITE_KEY, noteId + "", 1L);
            return true;
        } catch (Exception e) {
            log.error("减少点赞数失败: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean favorite(Long noteId, Long userId) {
        String key = userId + "::" + noteId;
        Note note = noteService.selectById(noteId);
        if (note == null) {
            throw GlobalException.from(ResultCode.NOTE_NOT_EXIST);
        }
        try {
            Integer status = (Integer) redisService.hGet(NoteConstant.USER_NOTE_FAVORITE_KEY, key);
            UserNoteFavorite favorite = userNoteFavoriteManager.getFavoriteByUserIdAndNoteId(userId, noteId);
            // 第一次收藏 数据库以及redis都没有数据
            if (status == null && favorite == null) {
                redisService.hSet(NoteConstant.USER_NOTE_FAVORITE_KEY, key, 1);
                this.increaseFavoriteCount(noteId);
            } else if (status != null && status == 0 && (favorite == null || favorite.getStatus() == 0)) {
                // 之前取消收藏了，现在重新收藏
                redisService.hSet(NoteConstant.USER_NOTE_FAVORITE_KEY, key, 1);
                this.increaseFavoriteCount(noteId);
            } else if (status == null && favorite.getStatus() == 0) {
                redisService.hSet(NoteConstant.USER_NOTE_FAVORITE_KEY, key, 1);
                this.increaseFavoriteCount(noteId);
            }
            return true;
        } catch (Exception e) {
            log.error("收藏失败: {}", e.getMessage(), e);
            return false;
        }
    }


    @Override
    public Boolean cancelFavorite(Long noteId, Long userId) {
        String key = userId + "::" + noteId;
        Note note = noteService.selectById(noteId);
        if (note == null) {
            throw GlobalException.from(ResultCode.NOTE_NOT_EXIST);
        }
        try {
            Integer status = (Integer) redisService.hGet(NoteConstant.USER_NOTE_FAVORITE_KEY, key);
            UserNoteFavorite favorite = userNoteFavoriteManager.getFavoriteByUserIdAndNoteId(userId, noteId);
            if (status == null && favorite.getStatus() == 1) {
                redisService.hSet(NoteConstant.USER_NOTE_FAVORITE_KEY, key, 0);
                this.decreaseFavoriteCount(noteId);
            } else if (status != null && status == 1 && favorite.getStatus() == 1) {
                redisService.hSet(NoteConstant.USER_NOTE_FAVORITE_KEY, key, 0);
                this.decreaseFavoriteCount(noteId);
            }
            return true;
        } catch (Exception e) {
            log.error("取消收藏失败: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public void transFavoriteFromRedis2DB() {
        List<UserNoteFavorite> list = this.getFavoriteDataFromRedis();
        for (UserNoteFavorite favorite : list) {
            UserNoteFavorite uf = userNoteFavoriteManager.getFavoriteByUserIdAndNoteId(favorite.getUserId(), favorite.getNoteId());
            if (uf == null) {
                //没有记录，直接存入
                save(favorite);
            } else {
                //有记录，需要更新
                uf.setStatus(favorite.getStatus());
                updateById(uf);
            }
        }
    }

    @Override
    public void transFavoriteCountFromRedis2DB() {
        List<FavoriteCountVO> list = this.getFavoriteCountFromRedis();
        for (FavoriteCountVO favoriteCountVO : list) {
            Note note = noteService.selectById(favoriteCountVO.getNoteId());
            //点赞数量属于无关紧要的操作，出错无需抛异常
            if (note != null) {
                log.info("更新笔记收藏数，笔记id：{}，收藏数：{}", note.getId(), favoriteCountVO.getFavoriteCount());
                Integer likeNum = note.getLikeCount() + favoriteCountVO.getFavoriteCount();
                note.setLikeCount(likeNum);
                //更新点赞数量
                noteService.update(note);
            }
        }
    }


    public List<UserNoteFavorite> getFavoriteDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(NoteConstant.USER_NOTE_FAVORITE_KEY, ScanOptions.NONE);
        List<UserNoteFavorite> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 userId，noteId
            String[] split = key.split("::");
            Long userId = Long.valueOf(split[0]);
            Long noteId = Long.valueOf(split[1]);
            Integer value = (Integer) entry.getValue();

            UserNoteFavorite favorite = new UserNoteFavorite(userId, noteId, value);
            list.add(favorite);
            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(NoteConstant.USER_NOTE_FAVORITE_KEY, key);
        }
        return list;
    }


    public List<FavoriteCountVO> getFavoriteCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(NoteConstant.USER_NOTE_FAVORITE_COUNT_KEY, ScanOptions.NONE);
        List<FavoriteCountVO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储在 FavoriteCountVO
            String key = (String) map.getKey();
            FavoriteCountVO dto = new FavoriteCountVO(Long.valueOf(key), (Integer) map.getValue());
            list.add(dto);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(NoteConstant.USER_NOTE_FAVORITE_COUNT_KEY, key);
        }
        return list;
    }
}
