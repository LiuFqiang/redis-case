package org.pigliu.rediscase.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author liufuqiang
 */
@Service
@Slf4j
public class ThreadPoolService {

    private ThreadPoolExecutor threadPoolExecutor;

    public void threadPool() {
        if (threadPoolExecutor == null) {
            threadPoolExecutor =
                    new ThreadPoolExecutor(4, 4, 30, TimeUnit.MINUTES, new LinkedBlockingDeque<>());
        }
        for (int i = 0; i < 10; i++) {
            int finalI = i;
//            threadPoolExecutor.allowCoreThreadTimeOut(true);
            threadPoolExecutor.execute(() -> {
                log.info("thread name:{}, {}， pool size:{}", Thread.currentThread().getName(), finalI, threadPoolExecutor.getPoolSize());
            });
        }
    }

    private static class TaskThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(@NotNull Runnable r) {
            return null;
        }
    }
}
