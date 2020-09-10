package com.kuang_juc.bq;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueDemo {
    public static void main(String[] args) {
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

        new Thread(()->{
            String name = Thread.currentThread().getName();
            try {
                System.out.println(name + " put 1");
                synchronousQueue.put("1");
                System.out.println(name + " put 2");
                synchronousQueue.put("2");
                System.out.println(name + " put 3");
                synchronousQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T1").start();

        new Thread(()->{
            String name = Thread.currentThread().getName();
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(name + " get " + synchronousQueue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(name + " get " + synchronousQueue.take());
                TimeUnit.SECONDS.sleep(3);
                System.out.println(name + " get " + synchronousQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "T2").start();

    }
}
