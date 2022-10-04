package com.example.red.book.mq;

import com.example.red.book.constant.CommentConstant;
import com.example.red.book.entity.Comment;
import com.example.red.book.entity.Note;
import com.example.red.book.model.doc.CommentDoc;
import com.example.red.book.model.doc.NoteDoc;
import com.example.red.book.service.CommentService;
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
public class CommentListener {

    @Autowired
    CommentService commentService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = CommentConstant.INSERT_QUEUE_NAME),
            exchange = @Exchange(name = CommentConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            key = CommentConstant.INSERT_KEY
    ))
    public void listenCommentAdd(Comment comment) {
        System.out.println("监听到评论添加");
        log.info("监听到评论添加，评论为：{}", comment);
        CommentDoc commentDoc = new CommentDoc(comment);
        commentService.addEsDoc(commentDoc);
    }
}
