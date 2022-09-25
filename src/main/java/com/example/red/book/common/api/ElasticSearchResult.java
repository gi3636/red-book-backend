package com.example.red.book.common.api;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ElasticSearchResult<T> {

    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPage;
    private Long total;
    private List<T> list;

    public ElasticSearchResult(SearchResponse<T> searchResponse, Integer pageNum, Integer pageSize) {
        this.list = searchResponse.hits().hits().stream().map(Hit::source).collect(Collectors.toList());
        this.totalPage = (int) (searchResponse.hits().total().value() / pageSize);
        this.total = searchResponse.hits().total().value();
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

}
