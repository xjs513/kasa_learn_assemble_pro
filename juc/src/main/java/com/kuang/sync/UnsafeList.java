package com.kuang.sync;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class UnsafeList {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                synchronized (list) {
                    list.add(Thread.currentThread().getName());
                    System.out.println(list.size());
                }
            }).start();
        }

        // 不加延迟主线程会提前跑完
        /*try {
            TimeUnit.SECONDS.sleep(5L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(list.size()); // 8870*/
    }
}
