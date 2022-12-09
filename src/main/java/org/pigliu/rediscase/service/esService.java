package org.pigliu.rediscase.service;

import lombok.RequiredArgsConstructor;
import org.elasticsearch.client.RestHighLevelClient;

@RequiredArgsConstructor
public class esService {

    private final RestHighLevelClient oldHighLevelClient;
}
