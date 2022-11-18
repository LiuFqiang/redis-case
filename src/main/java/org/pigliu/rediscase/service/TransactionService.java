package org.pigliu.rediscase.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liufuqiang
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

    private final StringRedisTemplate stringRedisTemplate;

    public void test() {
        String key = "book:num";


        stringRedisTemplate.setEnableTransactionSupport(true);
        String num = stringRedisTemplate.opsForValue().get(key);
        Integer value = Integer.parseInt(num) * 2;
        stringRedisTemplate.multi();
        stringRedisTemplate.opsForValue().setIfPresent(key, String.valueOf(value));
        List<Object> exec = stringRedisTemplate.exec();
        System.out.println(exec);
    }

}
