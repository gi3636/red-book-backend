package com.example.red.book.service.impl;

import com.example.red.book.entity.Chat;
import com.example.red.book.mapper.ChatMapper;
import com.example.red.book.service.ChatService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 聊天表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2022-12-19
 */
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {

}
