package com.kuang_juc.rw;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        Mycache mycache = new Mycache();
        for (int i = 1; i <= 5; i++) {
            int tmp = i;
            new Thread(() -> {
                mycache.put(tmp+"", tmp+"");
            }, String.valueOf(i)).start();
        }

        for (int i = 1; i <= 5; i++) {
            int tmp = i;
            new Thread(() -> {
                mycache.get(tmp+"");
            }, String.valueOf(i)).start();
        }
    }
}


class Mycache{
    private volatile Map<String, Object> map = new HashMap<>();

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    // 存， 写
    public void put(String key, Object value){
        lock.writeLock().lock();

        try {
            String name = Thread.currentThread().getName();
            System.out.println(name + "写入" + key);
            TimeUnit.MILLISECONDS.sleep(2500);
            map.put(key, value);
            System.out.println(name + "写入OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    // 取， 读
    public void get(String key){
        lock.readLock().lock();

        try {
            String name = Thread.currentThread().getName();
            System.out.println(name + "读取" + key);
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object o = map.get(key);
            System.out.println(name + "读取OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }
}