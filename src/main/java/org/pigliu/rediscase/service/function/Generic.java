package org.pigliu.rediscase.service.function;

import lombok.Data;

import java.util.ArrayList;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/7/14
 */
@Data
public class Generic<T extends Message>{

    T data;

    public String getName() {
        return data.getName();
    }

    public static void main(String[] args) {

    }
}
