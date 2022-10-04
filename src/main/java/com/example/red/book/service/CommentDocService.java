package com.example.red.book.service;

import com.example.red.book.model.doc.CommentDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentDocService extends ElasticsearchRepository<CommentDoc, Long> {
}
