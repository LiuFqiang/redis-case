package org.pigliu.rediscase.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.Lifecycle;
import org.springframework.context.SmartLifecycle;
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
        this.cosume();
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
//            try {
////                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            handleMsg();
            log.info("cosume Thread:{}", Thread.currentThread().getName());
        }
    }
}
