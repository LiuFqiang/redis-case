package org.pigliu.rediscase.service;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.File;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisPipleService {

    private final StringRedisTemplate stringRedisTemplate;

    public void execute() {
//        List<Object> liu = stringRedisTemplate.execute((RedisConnection connection) -> {
//            Jedis jedis = (Jedis) connection.getNativeConnection();
//            Pipeline p = jedis.pipelined();
//            p.incrBy("liu", 1);
//            return p.syncAndReturnAll();
//        });
        byte[] bytes = FileUtil.readBytes("/Users/liufuqiang/Downloads/123.pdf");
        FileUtil.writeBytes(bytes, new File("/Users/liufuqiang/Downloads/456.pdf"));
    }
}
