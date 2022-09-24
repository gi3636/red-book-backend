package com.example.red.book.mq;


import com.example.red.book.constant.NoteMqConstant;
import com.example.red.book.entity.Note;
import com.example.red.book.model.vo.NoteVO;
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

    @RabbitListener(queues = NoteMqConstant.INSERT_QUEUE_NAME)
    public void listenNoteInsert(Note note) {
        log.info("监听到消息，数据：{}", note);
        note = noteService.selectByUserIdAndTitle(note.getUserId(), note.getTitle());
        if (note != null) {
            NoteVO noteVO = NoteVO.convert(note);
            noteService.addDoc(noteVO);
        }
    }

    @RabbitListener(queues = NoteMqConstant.DELETE_QUEUE_NAME)
    public void listenHotelDelete(Long id) {
        //noteService.deleteById(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = NoteMqConstant.UPDATE_QUEUE_NAME),
            exchange = @Exchange(name = NoteMqConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            key = NoteMqConstant.UPDATE_KEY
    ))
    public void listenNoteUpdate(long id) {
        Note note = noteService.selectById(id);
        if (note != null) {
            NoteVO noteVO = NoteVO.convert(note);
            noteService.updateDoc(noteVO);
        }
    }
}
