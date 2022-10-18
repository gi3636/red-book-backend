package com.example.red.book.controller;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.red.book.common.api.CommonResult;
import com.example.red.book.entity.Note;
import com.example.red.book.model.doc.NoteDoc;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import co.elastic.clients.elasticsearch._types.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "elasticsearch模块")
@RestController
@Slf4j
@RequestMapping("/api/elasticsearch")
public class ElasticsearchController {

    @Autowired
    private ElasticsearchClient esClient;
    private static final String indexName = "note";


    @GetMapping("getDoc")
    public Note searchDoc() {
        String id = "1";
        GetRequest request = new GetRequest.Builder()
                .index(indexName)
                .id(id)
                .build();
        try {
            GetResponse<Note> fileDocumentGetResponse = esClient.get(request, Note.class);
            return fileDocumentGetResponse.source();
        } catch (IOException e) {
            log.error("ElasticUtils-searchDoc::{}", e.getMessage(), e);
            return null;
        }
    }


    @GetMapping("updateDoc")
    public Note updateDoc() {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("测试111");
        note.setContent("测试111");
        note.setUserId(1L);
        UpdateRequest<Note, Note> request = new UpdateRequest.Builder<Note, Note>()
                .id(note.getId() + "")
                .index(indexName)
                .doc(note)
                .build();
        try {
            UpdateResponse<Note> fileDocumentGetResponse = esClient.update(request, Note.class);
            log.info("更新文档操作 ===={} ", fileDocumentGetResponse.result());
            return null;
        } catch (IOException e) {
            log.error("ElasticUtils-searchDoc::{}", e.getMessage(), e);
            return null;
        }
    }


    @GetMapping("delDoc")
    public boolean delDoc() {
        String id = "1";
        DeleteRequest request = new DeleteRequest.Builder()
                .index(indexName)
                .id(id)
                .build();
        try {
            DeleteResponse delete = esClient.delete(request);
            System.out.println(delete.result().jsonValue());
            log.info("删除文档操作 ===={} ", delete.result());
            return delete.result() != null;
        } catch (IOException e) {
            log.error("ElasticUtils-delDoc::{}", e.getMessage(), e);
            return false;
        }
    }

    @GetMapping("addBatchDoc")
    public boolean addBatchDoc() throws IOException {
        // 使用 mybatis-plus 获取所有的记录
        List<Note> noteList = new ArrayList<>();
        for (int i = 11; i < 20; i++) {
            Note note = new Note();
            note.setId(i + 1L);
            note.setTitle("测试" + i);
            note.setContent("测试" + i);
            note.setUserId(1L);
            note.setFavoriteCount(1);
            note.setLikeCount(1);
            noteList.add(note);
        }
        BulkRequest.Builder builder = new BulkRequest.Builder();
        // 设置索引库
        builder.index("hotel");

        // 遍历记录
        for (Note note : noteList) {
            // 添加数据
            builder.operations(o -> o //lambda
                    .create(v -> v //lambda
                            // 读入 id
                            .id(note.getId().toString())
                            // 读入文档
                            .document(note)
                            // 设置索引库
                            .index(indexName)
                    )
            );
        }

        // 执行批量插入，并获取是否发生了错误

        BulkResponse bulk = esClient.bulk(builder.build());
        log.info("增加文档操作 ===={} ", bulk.errors());
        log.info("增加文档操作 ===={} ", bulk.items().size());
        return esClient.bulk(builder.build()).errors();
    }


    @GetMapping("queryDoc")
    public CommonResult<List<NoteDoc>> queryDoc() throws IOException {
        // 不公开不显示
        BoolQuery boolQuery = new BoolQuery.Builder()
                .mustNot(new TermQuery.Builder().field("isPublic").value("false").build()._toQuery())
                .build();

        MatchQuery matchQuery = new MatchQuery.Builder()
                .query("test")
                .field("title")
                .field("content")
                .build();

        SortOptions sortOptions = new SortOptions.Builder()
                .field(f -> f.field("createdTime").order(SortOrder.Asc))
                .build();

        SearchRequest request = new SearchRequest.Builder()
                .from(0)
                .size(20)
                .sort(sortOptions)
                .index(indexName)
                .query(boolQuery._toQuery())
                .query(matchQuery._toQuery())
                .build();

        SearchResponse<NoteDoc> search =
                esClient.search(
                        request,
                        NoteDoc.class
                );

        long total = search.hits().total().value();
        log.info("查询文档操作 ===={} ", search.hits());
        log.info("total:{}", total);
        List<NoteDoc> noteList = search.hits().hits().stream().map(Hit::source).collect(Collectors.toList());
        return CommonResult.success(noteList);
    }
}


