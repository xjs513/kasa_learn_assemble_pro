package com.kuang_juc.pool;

import java.util.concurrent.*;


/**
 * Executors 工具类  三大方法
 */
public class Demo01 {
    public static void main(String[] args) {
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();
//        threadPool = Executors.newFixedThreadPool(5);
//        threadPool = Executors.newCachedThreadPool();
//        ExecutorService threadPool = Executors.newScheduledThreadPool(5);


        // maximumPoolSize 如何定义???
        // 1. CPU 密集型 等于机器核心数 保证 CPU 效率最高  Runtime.getRuntime().availableProcessors()
        // 2. IO  密集型 判断程序中，消耗 IO 的线程有多少个，然后进行设置 比如设置 2 倍


        ExecutorService threadPool = new ThreadPoolExecutor(
                2, Runtime.getRuntime().availableProcessors(),
                5000L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                // new ThreadPoolExecutor.AbortPolicy()         // 拒绝             抛出异常
                // new ThreadPoolExecutor.DiscardPolicy()       // 拒绝             无异常抛出
                // new ThreadPoolExecutor.CallerRunsPolicy()    // 从哪里来到哪里去  无异常抛出
                new ThreadPoolExecutor.DiscardOldestPolicy()    // 尝试和第一个竞争  无异常抛出
        );


        try {
            for (int i = 0; i < 9; i++) {
//                threadPool.execute(()->{
//                    System.out.println(Thread.currentThread().getName() + ":OK");
//                });
                threadPool.execute(()->{
                    try {
                        TimeUnit.MILLISECONDS.sleep(1500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ":OK");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }


    }
}
