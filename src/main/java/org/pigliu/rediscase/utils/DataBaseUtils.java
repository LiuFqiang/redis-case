package org.pigliu.rediscase.utils;

import cn.hutool.core.util.StrUtil;
import com.google.protobuf.ServiceException;
import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.*;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/8/19
 */
public class DataBaseUtils {

    /**
     * 根据分表的字段值和拆分表个数，计算分布在哪个表上
     *
     * @param key 拆分表字段值
     * @param mod 拆分表个数
     * @return
     */
    public static int shardingNum(String key, int mod) {
        if (StrUtil.isEmpty(key) || mod <= 0) {
            throw new RuntimeException("error,str cann't be empty,or mod cann't be 0 or negative！");
        }
        String md5 = md5Key(key);
        System.out.println(md5);
        System.out.println(Integer.parseInt(md5.substring(0, 2), 16));
        System.out.println(md5.substring(0, 2));
        return Integer.parseInt(md5.substring(0, 2), 16) % mod;
    }

    /**
     * spring md5
     */
    private static String md5Key(String value) {
        try {
            // 16进制
            return DigestUtils.md5DigestAsHex(value.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException");
        }
    }

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(1);
        Future<Integer> submit = service.submit(() -> {
            throw new RuntimeException("errorss");
        });

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 40, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10)) {
            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
            }
        };

    }
}
