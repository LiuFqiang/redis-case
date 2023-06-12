package org.pigliu.rediscase.service;

import cn.hutool.core.thread.ThreadUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

/**
 * ThreadLocal测试
 *
 * @author liufuqiang
 * @since 2023/3/30
 */
public class ThreadLocalService {

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");

    public static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal();


    static ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();

    static ThreadLocal<String> strThreadLocal = new ThreadLocal<>();


    public static void main(String[] args) throws InterruptedException {
//        System.out.println(String.format("main thread init value -- %s", strThreadLocal.get()));
//        strThreadLocal.set("main");
//        stringThreadLocal.set("111");
//        System.out.println(stringThreadLocal.get());
//        System.out.println(String.format("main thread value --- %s", strThreadLocal.get()));
//        Thread thread = new Thread(() -> {
//            System.out.println(String.format("thread1 init value -- %s", strThreadLocal.get()));
//            strThreadLocal.set("thread1");
//            System.out.println(String.format("thread1 value -- %s", strThreadLocal.get()));
//        });
//        thread.start();
//        thread.join();
        testParentThreadLocal();
    }

    static ThreadLocal inheritableThreadLocal =  new InheritableThreadLocal<>();

    private static void testParentThreadLocal() throws InterruptedException {
        inheritableThreadLocal.set("parent");
        System.out.println("parent-- " + inheritableThreadLocal.get());
        Thread thread = new Thread(() -> {
            System.out.println("child-- " + inheritableThreadLocal.get());
        });
        thread.start();
        thread.join();
    }

    private static void testParentThread() throws InterruptedException {
        String init = stringThreadLocal.get();
        System.out.println(Thread.currentThread().getName() + "---" + init);
        Thread thread = new Thread(() -> {
            String init1 = stringThreadLocal.get();
            System.out.println(Thread.currentThread().getName() + "---" + init1);
        });
        thread.start();
        thread.join();
    }

    private static void testThreadLocal() {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executorService.execute(() -> {
                threadLocal.set(new SimpleDateFormat("mm:ss"));
                SimpleDateFormat simpleDateFormat = threadLocal.get();
                String format = simpleDateFormat.format(finalI * 1000);
                System.out.println(Thread.currentThread().getName() + "----" + format);
            });
        }
    }

    private static void testThreadPool() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executorService.execute(() -> {
                String format;
                synchronized (ThreadLocalService.class) {
                    format = simpleDateFormat.format(finalI * 1000);
                }
                System.out.println(Thread.currentThread().getName() + "----" + format);
            });
            TimeUnit.MILLISECONDS.sleep(10);
        }
    }

    private static String date(int seconds) {
        Date date = new Date(seconds * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        return simpleDateFormat.format(date);
    }

    private static void testSimpleDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                String date = simpleDateFormat.format(new Date(finalI * 1000));
                System.out.println(Thread.currentThread().getName() + "----" + date);
            }).start();
        }
    }

}
