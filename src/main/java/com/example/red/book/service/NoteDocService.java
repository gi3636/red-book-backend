package com.example.red.book.service;

import com.example.red.book.model.doc.NoteDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteDocService extends ElasticsearchRepository<NoteDoc, Long> {
}
