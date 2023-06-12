package org.pigliu.rediscase.dto;

import org.springframework.beans.factory.DisposableBean;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/3/30
 */
public class TestBean implements DisposableBean {
    @Override
    public void destroy() throws Exception {
        System.out.println(this.getClass() + "is destory");
    }
}
