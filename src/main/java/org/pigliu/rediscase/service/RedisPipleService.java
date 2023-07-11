package org.pigliu.rediscase.service;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
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
import java.util.Arrays;
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
//        byte[] bytes = FileUtil.readBytes("/Users/liufuqiang/Downloads/123.pdf");
//        FileUtil.writeBytes(bytes, new File("/Users/liufuqiang/Downloads/456.pdf"));
        stringRedisTemplate.opsForValue().set("test1", "我的老婆潘金莲");
        System.out.println("1111");
    }

    public int testBit() {
        String key = "vipBit";
        byte[] result = stringRedisTemplate.execute((RedisConnection connection) -> {
            Jedis jedis = (Jedis) connection.getNativeConnection();
            return jedis.get(key.getBytes());
        });
        int vipBit = 0;
        if (result == null) {
            return vipBit;
        }
        StringBuilder builder = new StringBuilder();
        for (byte b : result) {
            String s = Integer.toBinaryString(b & 0xff);
            builder.append(leftPad(s, 8, "0"));
        }
        String str = builder.toString();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                vipBit = vipBit | i;
            }
        }
        return vipBit;
    }

    public static String leftPad(String str, int size, String padStr) {
        if (str == null) {
            return null;
        } else {
            if (StrUtil.isEmpty(padStr)) {
                padStr = " ";
            }

            int padLen = padStr.length();
            int strLen = str.length();
            int pads = size - strLen;
            if (pads <= 0) {
                return str;
            } else if (padLen == 1 && pads <= 8192) {
                return leftPad(str, size, padStr.charAt(0));
            } else if (pads == padLen) {
                return padStr.concat(str);
            } else if (pads < padLen) {
                return padStr.substring(0, pads).concat(str);
            } else {
                char[] padding = new char[pads];
                char[] padChars = padStr.toCharArray();

                for(int i = 0; i < pads; ++i) {
                    padding[i] = padChars[i % padLen];
                }

                return (new String(padding)).concat(str);
            }
        }
    }

    public static String leftPad(String str, int size, char padChar) {
        if (str == null) {
            return null;
        } else {
            int pads = size - str.length();
            if (pads <= 0) {
                return str;
            } else {
                return pads > 8192 ? leftPad(str, size, String.valueOf(padChar)) : repeat(padChar, pads).concat(str);
            }
        }
    }

    public static String repeat(char ch, int repeat) {
        if (repeat <= 0) {
            return "";
        } else {
            char[] buf = new char[repeat];
            Arrays.fill(buf, ch);
            return new String(buf);
        }
    }
}
