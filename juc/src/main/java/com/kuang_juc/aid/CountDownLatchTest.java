package com.kuang_juc.aid;

import java.util.concurrent.CountDownLatch;

/**
 * 计数器
 *
 *
 */
public class CountDownLatchTest {
    public static void main(String[] args) {
        CountDownLatchTest countDownLatchTest = new CountDownLatchTest();
        countDownLatchTest.test();
    }

    // 一个线程等待多个X发生后才能继续
    public void test2(){
        // 总数是 6
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "Go out.");
                countDownLatch.countDown(); // 计数器减一
            }, String.valueOf(i)).start();
        }

        // 等待计数器归零，然后继续向下进行
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Close Door..");
    }


    // 多个线程等待 X 发生后才能继续
    public void test() {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "await 前");
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "await 后");
            }, String.valueOf(i)).start();
        }


        try {
            Thread.sleep(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        countDownLatch.countDown();
        System.out.println("完成");

    }
}
