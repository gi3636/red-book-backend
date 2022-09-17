package com.example.red.book.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.entity.Note;
import com.example.red.book.mapper.NoteMapper;
import com.example.red.book.service.NoteService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author franky
 * @since 2022-09-17
 */
@Service
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements NoteService {

}
