package com.example.red.book.mq;


import com.example.red.book.constant.NoteMqConstant;
import com.example.red.book.entity.Note;
import com.example.red.book.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NoteListener {

    @Autowired
    NoteService noteService;

    @RabbitListener(queues = NoteMqConstant.INSERT_QUEUE_NAME)
    public void listenHotelInsertOrUpdate(Note note) {
        log.info("receive note insert message: {}", note);
        //noteService.insertById(id);
    }

    @RabbitListener(queues = NoteMqConstant.DELETE_QUEUE_NAME)
    public void listenHotelDelete(Long id) {
        //noteService.deleteById(id);
    }


}
