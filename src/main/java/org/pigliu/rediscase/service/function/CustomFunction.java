package org.pigliu.rediscase.service.function;

import org.pigliu.rediscase.dto.BookInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/7/12
 */
@FunctionalInterface
public interface CustomFunction {

    void sendMsg(String msg);

}
