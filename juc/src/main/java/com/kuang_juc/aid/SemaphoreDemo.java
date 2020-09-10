package com.kuang_juc.aid;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                String name = Thread.currentThread().getName();
                try {
//                    semaphore.acquire();
                    semaphore.tryAcquire();
                    System.out.println(name + "抢到车位");
                    TimeUnit.SECONDS.sleep(2L);
                    System.out.println(name + "离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }
}
