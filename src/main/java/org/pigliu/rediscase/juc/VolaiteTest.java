package org.pigliu.rediscase.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/8/2
 */
public class VolaiteTest {

    public static  int a;

    public static AtomicBoolean running = new AtomicBoolean(true);

    private void testA() {
        while (running.get()) {

        }
        System.out.println(running.get());
    }

    public static void main(String[] args) throws InterruptedException {
        VolaiteTest volaiteTest = new VolaiteTest();
        new Thread(() -> volaiteTest.testA(), "Thread-A").start();

        TimeUnit.SECONDS.sleep(10);
        VolaiteTest.running.getAndSet( false);
    }
}
