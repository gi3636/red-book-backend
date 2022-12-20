package com.example.red.book.mq;

import com.example.red.book.constant.MessageConstant;
import com.example.red.book.entity.Message;
import com.example.red.book.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageListener {

    @Autowired
    MessageService messageService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MessageConstant.INSERT_QUEUE_NAME),
            exchange = @Exchange(name = MessageConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            key = MessageConstant.INSERT_KEY
    ))
    public void listenMessageAdd(Message message) {
        //TODO 消息入库
        System.out.println("监听到消息添加");
        System.out.println(message);
    }
}
