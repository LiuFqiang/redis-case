package org.pigliu.rediscase.service;

import java.util.Collections;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/6/14
 */
public class RedisQueue implements Queue{
    @Override
    public boolean sendMsg() {

        return false;
    }


    @Override
    public String buildTitle() {
        return null;
    }

    public static void main(String[] args) {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("redisQueu");
    }
}
