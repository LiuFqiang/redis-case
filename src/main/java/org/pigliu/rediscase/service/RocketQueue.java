package org.pigliu.rediscase.service;

import javafx.beans.NamedArg;
import org.elasticsearch.common.inject.name.Named;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/6/14
 */
@Component("rocket")
public class RocketQueue implements Queue, BeanNameAware {
    @Override
    public boolean sendMsg() {
        System.out.println("rocter queue");
        System.out.println(beanName);
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("rocketQueue");
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz = Class.forName("org.pigliu.rediscase.service.RocketQueue");
        Constructor<?>[] ator = clazz.getDeclaredConstructors();

    }

    private String beanName;

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
