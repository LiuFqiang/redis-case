package org.pigliu.rediscase.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pigliu.rediscase.mapper.DynamicMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DynamicSelectService {

    private final DynamicMapper dynamicMapper;

    public void queryList() {
        dynamicMapper.queryUserDB(null, "b_label", null, null, resultContext -> {
            Map<String, Object> resultObject = resultContext.getResultObject();
            log.info("resultObject:{}", resultObject);
            log.info("count:{}", resultContext.getResultCount());
        });
    }
}
