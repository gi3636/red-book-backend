package com.example.red.book.controller;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.mapping.KeywordProperty;
import co.elastic.clients.elasticsearch._types.mapping.Property;
import co.elastic.clients.elasticsearch._types.mapping.TextProperty;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.elasticsearch.core.bulk.IndexOperation;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import com.example.red.book.config.ElasticSearchClientConfig;
import com.example.red.book.entity.Note;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "elasticsearch模块")
@RestController
@Slf4j
@RequestMapping("/api/elasticsearch")
public class ElasticsearchController {

    @Autowired
    private ElasticSearchClientConfig elasticSearchClientConfig;
    private static final String indexName = "note";


    @GetMapping("/init")
    public Boolean createEsIndex() {
        //定义文档属性
        Map<String, Property> propertyMap = new HashMap<>();
        propertyMap.put("id", new Property(new KeywordProperty.Builder().build()));
        propertyMap.put("title", new Property(new TextProperty.Builder().analyzer("ik_max_word").searchAnalyzer("ik_smart").build()));
        propertyMap.put("content", new Property(new TextProperty.Builder().analyzer("ik_max_word").searchAnalyzer("ik_smart").build()));
        // 设置索引的文档类型映射
        TypeMapping typeMapping = new TypeMapping.Builder()
                .properties(propertyMap)
                .build();
        try {
            // 创建索引
            ElasticsearchClient client = elasticSearchClientConfig.elasticsearchClient();
            CreateIndexResponse createIndexResponse = client.indices().create(b -> b
                    .index(indexName)
                    .mappings(typeMapping)
            );
            System.out.println("创建索引成功: " + createIndexResponse);
            // 响应状态
            log.info("增加索引操作 ===={} ", createIndexResponse.acknowledged());
            log.info("增加索引操作 ===={} ", createIndexResponse.index());
            log.info("增加索引操作 ===={} ", createIndexResponse.shardsAcknowledged());
        } catch (IOException e) {
            log.error("es InsertError ---- {}", e.getMessage());
        }
        return true;
    }


    @GetMapping("addDoc")
    public boolean addDoc() {
        ElasticsearchClient client = elasticSearchClientConfig.elasticsearchClient();
        Note note = new Note();
        note.setId(1L);
        note.setTitle("测试");
        note.setContent("测试");
        note.setUserId(1L);
        note.setFollowCount(1);
        note.setLikeCount(1);
        IndexRequest<Note> request = new IndexRequest.Builder<Note>()
                .index(indexName)
                // 设置文档id
                .id(note.getId() + "")
                // 设置文档
                .document(note)
                // 刷新
                .refresh(Refresh.True)
                .build();
        try {
            IndexResponse response = client.index(request);
            log.info("增加文档操作 ===={} ", response.result());
            System.out.println(response.result().jsonValue());
            return response.result() != null;
        } catch (IOException e) {
            log.error("ElasticUtils-addDoc::{}", e.getMessage(), e);
            return false;
        }
    }


    @GetMapping("getDoc")
    public Note searchDoc() {
        ElasticsearchClient client = elasticSearchClientConfig.elasticsearchClient();
        String id = "1";
        GetRequest request = new GetRequest.Builder()
                .index(indexName)
                .id(id)
                .build();
        try {
            GetResponse<Note> fileDocumentGetResponse = client.get(request, Note.class);
            return fileDocumentGetResponse.source();
        } catch (IOException e) {
            log.error("ElasticUtils-searchDoc::{}", e.getMessage(), e);
            return null;
        }
    }


    @GetMapping("updateDoc")
    public Note updateDoc() {
        ElasticsearchClient client = elasticSearchClientConfig.elasticsearchClient();
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
            UpdateResponse<Note> fileDocumentGetResponse = client.update(request, Note.class);
            log.info("更新文档操作 ===={} ", fileDocumentGetResponse.result());
            return null;
        } catch (IOException e) {
            log.error("ElasticUtils-searchDoc::{}", e.getMessage(), e);
            return null;
        }
    }


    @GetMapping("delDoc")
    public boolean delDoc() {
        ElasticsearchClient client = elasticSearchClientConfig.elasticsearchClient();
        String id = "1";
        DeleteRequest request = new DeleteRequest.Builder()
                .index(indexName)
                .id(id)
                .build();
        try {
            DeleteResponse delete = client.delete(request);
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
        ElasticsearchClient client = elasticSearchClientConfig.elasticsearchClient();
        // 使用 mybatis-plus 获取所有的记录
        List<Note> noteList = new ArrayList<>();
        for (int i = 11; i < 20; i++) {
            Note note = new Note();
            note.setId(i + 1L);
            note.setTitle("测试" + i);
            note.setContent("测试" + i);
            note.setUserId(1L);
            note.setFollowCount(1);
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

        BulkResponse bulk = client.bulk(builder.build());
        log.info("增加文档操作 ===={} ", bulk.errors());
        log.info("增加文档操作 ===={} ", bulk.items().size());
        return client.bulk(builder.build()).errors();
    }

}


