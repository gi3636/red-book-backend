package com.example.red.book.service.impl;

import com.example.red.book.entity.Message;
import com.example.red.book.mapper.MessageMapper;
import com.example.red.book.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2022-12-19
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

}
