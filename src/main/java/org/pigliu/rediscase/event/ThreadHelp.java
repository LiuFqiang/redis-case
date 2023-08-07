package org.pigliu.rediscase.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.Lifecycle;
import org.springframework.context.SmartLifecycle;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/7/25
 */
@Slf4j
public class ThreadHelp implements SmartLifecycle, Runnable {

    private volatile boolean running = false;

    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void start() {
        log.info("spring container is started");
        running = true;
        threadPoolExecutor = new ThreadPoolExecutor(4, 4,
                        60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < 4; i++) {
            this.cosume();
        }
    }

    @Override
    public void stop() {
        running = false;
        threadPoolExecutor.shutdown();
        log.info("spring container is stop");
    }

    @Override
    public boolean isRunning() {
        log.info("spring container running status: {}", running);
        return running;
    }

    public void cosume() {
        threadPoolExecutor.execute(this);
    }

    public void handleMsg() {

    }

    @Override
    public void run() {
        while (running) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handleMsg();
            log.info("cosume Thread:{}", Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                1,
                10,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(1);
        taskExecutor.execute(() -> {
            System.out.println(1);
            System.out.println("线程池线程名字" + Thread.currentThread().getName());
        });
        System.out.println("主线程名字" + Thread.currentThread().getName());
        TimeUnit.SECONDS.sleep(10);
    }
}
