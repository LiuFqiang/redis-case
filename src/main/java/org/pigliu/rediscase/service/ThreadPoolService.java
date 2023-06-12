package org.pigliu.rediscase.service;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.pigliu.rediscase.dto.request.QueryResponse;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.*;

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

    public void testShutdown() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "---" + finalI);
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();

        while (!executorService.isTerminated()) {
            System.out.println(1);
        }

        System.out.println(executorService.isShutdown());
        System.out.println(executorService.isTerminated());
    }

    public static void main(String[] args) {

    }

    private static class TaskThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(@NotNull Runnable r) {
            return null;
        }
    }
}
