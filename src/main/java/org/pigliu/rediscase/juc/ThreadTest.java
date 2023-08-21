package org.pigliu.rediscase.juc;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/8/18
 */
@Slf4j
public class ThreadTest extends Thread{

    private static ThreadLocal<String> threadLocal = new ThreadLocal();

    private Integer i = 0;
    private CyclicBarrier barrier;
    public ThreadTest(int i, CyclicBarrier cyclicBarrier) {
        this.i = i;
        this.barrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(2);
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        log.info("thread name: {}, i: {}", Thread.currentThread().getName(), i);
    }

    public static void main(String[] args) {
//        CyclicBarrier barrier = new CyclicBarrier(3);
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(
//                3, 3, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
//                new ThreadPoolExecutor.DiscardPolicy());
//        for (int i = 0; i < 10; i++) {
//            executor.execute(new ThreadTest(i, barrier));
//        }
        LinkedBlockingQueue<Object> linkedQueue = new LinkedBlockingQueue<>();
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(10);
        PriorityBlockingQueue<String> priorityQueue = new PriorityBlockingQueue<>();
        SynchronousQueue<Object> objects = new SynchronousQueue<>();
        for (int i = 0; i < 20; i++) {
            boolean offer = queue.offer("1");
            System.out.println(offer + "" +  i);

            linkedQueue.offer("11");

        }
    }
}
