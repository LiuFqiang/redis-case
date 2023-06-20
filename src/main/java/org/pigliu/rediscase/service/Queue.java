package org.pigliu.rediscase.service;

import org.elasticsearch.core.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;


/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/6/14
 */
public interface Queue extends InitializingBean {


    public String name = null;




    boolean sendMsg();


    default String buildTitle() {
        return "queue";
    }
}
