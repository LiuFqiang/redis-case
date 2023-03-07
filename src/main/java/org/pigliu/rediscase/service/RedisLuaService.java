package org.pigliu.rediscase.service;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.Collections;
import java.util.List;

/**
 * @author liufuqiang
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RedisLuaService {
    
    private final StringRedisTemplate stringRedisTemplate;

    private final RedisTemplate redisTemplate;

    public void handlePipelined() {
        String key = "key_pipeline";
        String value = "apple";
        List<Object> execute = stringRedisTemplate.execute((RedisConnection connection) -> {
            Jedis jedis = (Jedis) connection.getNativeConnection();
            Pipeline p = jedis.pipelined();
            p.lrem(key, 1, value);
            p.lpush(key, value);
            return p.syncAndReturnAll();
        });
        System.out.println(JSONUtil.toJsonStr(execute));
    }

    public void refreshRank() {
        String totalKey = "rank:total";
        String monthKey = "rank:total_2023_03";
        String weekKey = "rank:week_20230305_20230312";
        String bookId = "100019349";
        List<Object> execute = stringRedisTemplate.execute((RedisConnection connection) -> {
            Jedis jedis = (Jedis) connection.getNativeConnection();
            Pipeline p = jedis.pipelined();
            p.zincrby(totalKey, 1, bookId);
            p.zincrby(monthKey, 1, bookId);
            p.zincrby(weekKey, 1, bookId);
            return p.syncAndReturnAll();
        });
        System.out.println(JSONUtil.toJsonStr(execute));
    }

    public void remListLua() {
        // 这样
        String key = "list1";
        String script = "redis.call('lrem', KEYS[1], 1, ARGV[1]) return redis.call('rpush', KEYS[1], ARGV[1])";
        Object execute = stringRedisTemplate.execute((RedisConnection connection) -> {
            Jedis jedis = (Jedis) connection.getNativeConnection();
           return jedis.eval(script, 1, key, "apple");
        });
        System.out.println(execute);

        // 或者这样
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(script);
        redisScript.setResultType(String.class);
        stringRedisTemplate.execute(redisScript, Collections.singletonList(key), "apple");
    }
}
