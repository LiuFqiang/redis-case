package org.pigliu.rediscase.service;

import javafx.beans.NamedArg;
import org.elasticsearch.common.inject.name.Named;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/6/14
 */
@Component
public class RocketQueue implements Queue{
    @Override
    public boolean sendMsg() {
        System.out.println("rocter queue");
        return false;
    }

    @Bean
    public RocketQueue newQueue() {
        return new RocketQueue();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("rocketQueue");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = Class.forName("org.pigliu.rediscase.service.RocketQueue");
        Constructor<?>[] ator = clazz.getDeclaredConstructors();

    }
}
