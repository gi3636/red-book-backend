package com.example.red.book.service.impl;

import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.common.service.RedisService;
import com.example.red.book.constant.NoteConstant;
import com.example.red.book.entity.Note;
import com.example.red.book.entity.UserNoteLike;
import com.example.red.book.manager.UserNoteLikeManager;
import com.example.red.book.mapper.UserNoteLikeMapper;
import com.example.red.book.model.vo.LikeCountVO;
import com.example.red.book.service.NoteService;
import com.example.red.book.service.UserNoteLikeService;
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
 * 笔记点赞数表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2022-09-19
 */
@Slf4j
@Service
public class UserNoteLikeServiceImpl extends ServiceImpl<UserNoteLikeMapper, UserNoteLike> implements UserNoteLikeService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserNoteLikeManager userNoteLikeManager;

    @Autowired
    private NoteService noteService;

    @Autowired
    private RedisService redisService;


    @Override
    public Boolean like(Long noteId, Long userId) {
        String key = userId + "::" + noteId;
        Note note = noteService.selectById(noteId);
        if (note == null) {
            throw GlobalException.from(ResultCode.NOTE_NOT_EXIST);
        }
        try {
            redisService.hSet(NoteConstant.USER_NOTE_LIKE_KEY, key, 1);
            this.increaseLikeCount(noteId);
            return true;
        } catch (Exception e) {
            log.error("点赞失败: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean unlike(Long noteId, Long userId) {
        String key = userId + "::" + noteId;
        Note note = noteService.selectById(noteId);
        if (note == null) {
            throw GlobalException.from(ResultCode.NOTE_NOT_EXIST);
        }
        try {
            redisService.hSet(NoteConstant.USER_NOTE_LIKE_KEY, key, 0);
            this.decreaseLikeCount(noteId);
            return true;
        } catch (Exception e) {
            log.error("取消点赞失败: {}", e.getMessage(), e);
            return false;
        }
    }


    @Override
    public List<UserNoteLike> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(NoteConstant.USER_NOTE_LIKE_KEY, ScanOptions.NONE);
        List<UserNoteLike> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 userId，noteId
            String[] split = key.split("::");
            Long userId = Long.valueOf(split[0]);
            Long noteId = Long.valueOf(split[1]);
            Integer value = (Integer) entry.getValue();

            UserNoteLike userLike = new UserNoteLike(userId, noteId, value);
            list.add(userLike);
            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(NoteConstant.USER_NOTE_LIKE_KEY, key);
        }
        return list;
    }


    @Override
    public List<LikeCountVO> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(NoteConstant.USER_NOTE_LIKE_COUNT_KEY, ScanOptions.NONE);
        List<LikeCountVO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储在 LikeCountVO
            String key = (String) map.getKey();
            LikeCountVO dto = new LikeCountVO(Long.valueOf(key), (Integer) map.getValue());
            list.add(dto);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(NoteConstant.USER_NOTE_LIKE_COUNT_KEY, key);
        }
        return list;
    }

    @Override
    public void transLikedFromRedis2DB() {
        List<UserNoteLike> list = this.getLikedDataFromRedis();
        for (UserNoteLike like : list) {
            UserNoteLike ul = userNoteLikeManager.getLikeByUserIdAndNoteId(like.getUserId(), like.getNoteId());
            if (ul == null) {
                //没有记录，直接存入
                save(like);
            } else {
                //有记录，需要更新
                ul.setStatus(like.getStatus());
                updateById(ul);
            }
        }
    }

    @Override
    public void transLikedCountFromRedis2DB() {
        List<LikeCountVO> list = this.getLikedCountFromRedis();
        for (LikeCountVO likeCountVO : list) {
            Note note = noteService.selectById(likeCountVO.getNoteId());
            //点赞数量属于无关紧要的操作，出错无需抛异常
            if (note != null) {
                log.info("更新笔记点赞数，笔记id：{}，点赞数：{}", note.getId(), likeCountVO.getLikeCount());
                Integer likeNum = note.getLikeCount() + likeCountVO.getLikeCount();
                note.setLikeCount(likeNum);
                //更新点赞数量
                noteService.update(note);
            }
        }
    }


    @Override
    public Boolean increaseLikeCount(Long noteId) {
        try {
            redisService.hIncr(NoteConstant.USER_NOTE_LIKE_COUNT_KEY, noteId + "", 1L);
            return true;
        } catch (Exception e) {
            log.error("增加点赞数失败: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean decreaseLikeCount(Long noteId) {
        try {
            redisService.hDecr(NoteConstant.USER_NOTE_LIKE_COUNT_KEY, noteId + "", 1L);
            return true;
        } catch (Exception e) {
            log.error("减少点赞数失败: {}", e.getMessage(), e);
            return false;
        }
    }
}
