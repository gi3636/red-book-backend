package com.example.red.book.mq;

import com.example.red.book.constant.CommentConstant;
import com.example.red.book.entity.Note;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class CommentListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = CommentConstant.INSERT_KEY),
            exchange = @Exchange(name = CommentConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            key = CommentConstant.INSERT_KEY
    ))
    public void listenCommentAdd(Note note) {

    }
}
