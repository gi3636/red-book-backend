package com.example.red.book.manager;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.red.book.common.api.ElasticSearchResult;
import com.example.red.book.common.api.ResultCode;
import com.example.red.book.common.exception.GlobalException;
import com.example.red.book.constant.NoteConstant;
import com.example.red.book.entity.Comment;
import com.example.red.book.mapper.CommentMapper;
import com.example.red.book.model.doc.CommentDoc;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.ExistsQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class CommentManager extends ServiceImpl<CommentMapper, Comment> {

    private static final String indexName = NoteConstant.INDEX;

    @Autowired
    private ElasticsearchClient esClient;

    public ElasticSearchResult<CommentDoc> getCommentByEsAndNoteId(Long noteId, Integer currentPage, Integer size) {
        BoolQuery boolQuery = new BoolQuery.Builder()
                //查询一级评论
                .mustNot(new ExistsQuery.Builder().field("parentId").build()._toQuery())
                .build();

        MatchQuery matchQuery = new MatchQuery.Builder()
                .query(noteId.toString())
                .field("noteId")
                .build();

        SortOptions sortOptions = new SortOptions.Builder()
                .field(f -> f.field("createdTime").order(SortOrder.Asc))
                .build();

        int start = (currentPage - 1) * size;

        SearchRequest request = new SearchRequest.Builder()
                .from(start)
                .size(size)
                .sort(sortOptions)
                .index(indexName)
                .query(matchQuery._toQuery())
                .query(boolQuery._toQuery())
                .build();

        ElasticSearchResult<CommentDoc> result;
        try {
            SearchResponse<CommentDoc> search = esClient.search(request, CommentDoc.class);
            result = new ElasticSearchResult<>(search, currentPage, size);
        } catch (Exception e) {
            log.error("查询数据：{}", noteId);
            log.error("查询文档操作失败: {} ", e.getMessage(), e);
            throw GlobalException.from(ResultCode.QueryError);
        }
        return result;
    }
}
