package com.kuang.gaoji;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {

    public static void main(String[] args) {
        TestLock2 testLock2 = new TestLock2();

        new Thread(testLock2, "A").start();
        new Thread(testLock2, "B").start();

    }

}

class TestLock2 implements Runnable{

    int ticketNum = 100;

    private final Lock lock   = new ReentrantLock();

    @Override
    public void run() {

        while (true){
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.lock();
            try {
                if (ticketNum > 0) {
                    System.out.println(Thread.currentThread().getName() + "[" + ticketNum-- + "]");
                } else {
                    System.out.println(Thread.currentThread().getName() + "：售罄....");
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}