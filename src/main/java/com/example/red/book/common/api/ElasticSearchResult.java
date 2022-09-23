package com.example.red.book.common.api;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.transport.ElasticsearchTransport;
import lombok.Data;
import org.elasticsearch.client.RestClient;

@Data
public class ElasticSearchResult {

    private RestClient restClient;

    private ElasticsearchTransport elasticsearchTransport;

    private ElasticsearchClient elasticsearchClient;

    public ElasticSearchResult(RestClient restClient, ElasticsearchTransport elasticsearchTransport, ElasticsearchClient elasticsearchClient) {
        this.restClient = restClient;
        this.elasticsearchTransport = elasticsearchTransport;
        this.elasticsearchClient = elasticsearchClient;
    }
}
