package org.pigliu.rediscase.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ElasticSearchConfig {

    @Bean
    public RestHighLevelClient oldHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("http://8.142.36.89/",9200,"http")
                )
        );
        return client;
    }
}
