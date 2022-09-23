package com.example.red.book.config;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class ElasticSearchClientConfig {
    @Value("${elasticsearch.http}")
    private String elasticSearchHttp;

    @Bean
    public ElasticsearchClient elasticsearchClient() {
        HttpHost[] httpHosts = getHttpHosts();
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        //账号密码
        //credentialsProvider.setCredentials(
        //        AuthScope.ANY, new UsernamePasswordCredentials("name", "password"));

        RestClientBuilder builder = RestClient.builder(httpHosts)
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

        RestClient restClient = builder.build();
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }

    private HttpHost[] getHttpHosts() {
        return Arrays.stream(elasticSearchHttp.split(",")).map(x -> {
            String[] hostInfo = x.split(":");
            return new HttpHost(hostInfo[0], Integer.parseInt(hostInfo[1]));
        }).toArray(HttpHost[]::new);
    }

}
