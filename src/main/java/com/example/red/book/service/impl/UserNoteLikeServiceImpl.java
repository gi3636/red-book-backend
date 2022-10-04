package com.example.red.book.service.impl;

import com.example.red.book.common.service.RedisService;
import com.example.red.book.constant.NoteConstant;
import com.example.red.book.entity.UserNoteLike;
import com.example.red.book.mapper.UserNoteLikeMapper;
import com.example.red.book.model.vo.LikeCountVO;
import com.example.red.book.service.UserNoteLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
@Service
public class UserNoteLikeServiceImpl extends ServiceImpl<UserNoteLikeMapper, UserNoteLike> implements UserNoteLikeService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<UserNoteLike> getLikeDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(NoteConstant.USER_NOTE_LIKE_KEY, ScanOptions.NONE);
        List<UserNoteLike> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 likedUserId，likedPostId
            String[] split = key.split("::");
            Long userId = Long.valueOf(split[0]);
            Long noteId = Long.valueOf(split[1]);
            Integer value = (Integer) entry.getValue();

            //组装成 UserLike 对象
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
        while (cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储在 LikedCountDT
            String key = (String)map.getKey();
            LikeCountVO dto = new LikeCountVO(key, (Integer) map.getValue());
            list.add(dto);
            //从Redis中删除这条记录
            redisTemplate.opsForHash().delete(NoteConstant.USER_NOTE_LIKE_COUNT_KEY, key);
        }
        return list;
    }
}
