package com.example.red.book.mq;


import com.example.red.book.constant.NoteConstant;
import com.example.red.book.entity.Note;
import com.example.red.book.model.doc.NoteDoc;
import com.example.red.book.service.NoteDocService;
import com.example.red.book.service.NoteService;
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
public class NoteListener {

    @Autowired
    NoteService noteService;

    @RabbitListener(queues = NoteConstant.INSERT_QUEUE_NAME)
    public void listenNoteInsert(Note note) {
        if (note != null) {
            log.info("监听到新增笔记消息，笔记为：{}", note);
            NoteDoc noteDoc = new NoteDoc(note);
            noteService.addEsDoc(noteDoc);
        }
    }

    @RabbitListener(queues = NoteConstant.DELETE_QUEUE_NAME)
    public void listenNoteDelete(Long id) {
        //noteService.deleteById(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = NoteConstant.UPDATE_QUEUE_NAME),
            exchange = @Exchange(name = NoteConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            key = NoteConstant.UPDATE_KEY
    ))
    public void listenNoteUpdate(Note note) {
        if (note != null) {
            NoteDoc noteDoc = new NoteDoc(note);
            noteService.updateEsDoc(noteDoc);
        }
    }
}
