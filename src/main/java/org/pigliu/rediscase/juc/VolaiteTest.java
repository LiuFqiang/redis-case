package org.pigliu.rediscase.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/8/2
 */
public class VolaiteTest {

    public static volatile int a;

    private void testA() {
        while (a != 100) {
//            System.out.println(a);
        }
        System.out.println(a);
    }

    public static void main(String[] args) throws InterruptedException {
        VolaiteTest volaiteTest = new VolaiteTest();
        new Thread(() -> volaiteTest.testA(), "Thread-A").start();

        TimeUnit.SECONDS.sleep(10);
        VolaiteTest.a = 100;
    }
}
