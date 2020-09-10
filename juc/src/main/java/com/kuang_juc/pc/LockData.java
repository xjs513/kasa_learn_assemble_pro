package com.kuang_juc.pc;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 判断等待  干活 通知
// Condition 精准的通知唤醒线程
public class LockData {
    private int number  = 0;

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    // +1
    public void increment()  {
        lock.lock();
        try {
            while (number != 0){
                // 等待
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            number++ ;
            System.out.println(Thread.currentThread().getName() + " => " + number);
            // 通知消费
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    // -1
    public void decrement()  {
        lock.lock();
        try {
            while (number == 0){
                // 等待
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            number-- ;
            System.out.println(Thread.currentThread().getName() + " => " + number);
            // 通知生产
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
