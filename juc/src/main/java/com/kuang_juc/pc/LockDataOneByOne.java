package com.kuang_juc.pc;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 判断等待  干活 通知
// Condition 精准的通知唤醒线程
public class LockDataOneByOne {
    private int number  = 1; //1=A 2=B 3=C

    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void printA(){
        lock.lock();
        try {
            while (number != 1){
                // wait
                condition1.await();
            }
            String name = Thread.currentThread().getName();
            TimeUnit.SECONDS.sleep(1L);
            System.out.println(name + " => AAAAAA");
            // 精确唤醒线程
            number = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB(){
        lock.lock();
        try {
            while (number != 2){
                // wait
                condition2.await();
            }
            String name = Thread.currentThread().getName();
            TimeUnit.SECONDS.sleep(1L);
            System.out.println(name + " => BBBBBB");
            // 精确唤醒线程
            number = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC(){
        lock.lock();
        try {
            while (number != 3){
                // wait
                condition3.await();
            }
            String name = Thread.currentThread().getName();
            TimeUnit.SECONDS.sleep(1L);
            System.out.println(name + " => CCCCCC");
            // 精确唤醒线程
            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
