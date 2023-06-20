package org.pigliu.rediscase.dto;

import lombok.NonNull;
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

    private static void notnull(@NonNull String key1) {
        System.out.println(key1);
    }

    public static void main(String[] args) {
        notnull("1");
        System.out.println();
    }
}
