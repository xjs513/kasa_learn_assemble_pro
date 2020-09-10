package com.kuang_juc.unsafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

public class ListTest {
    public static void main(String[] args) {

        int num = 1000;

        CountDownLatch countDownLatch = new CountDownLatch(num);

        // 并发下 ArrayList 不安全
//        List<String> list = new ArrayList<>();

        // 解决方案1：
//        List<String> list = new Vector<>();

        // 解决方案2：
//        List<String> list = Collections.synchronizedList(new ArrayList<>());

        // 解决方案3：
        List<String> list = new CopyOnWriteArrayList<>();

        /**
         * CopyOnWrite COW 一种优化策略
         * 多线程调用的时候，读取的时候，固定的，
         * 写入的时候避免覆盖造成数据问题！！
         *
         * CopyOnWriteArrayList 比 Vector 高效
         *
         * 1. 先会用 2. 货比三家，寻找其他解决方案 3. 分析源码
         *
         */

        // 单线程操作
//        for (int i = 0; i < 10; i++) {
////            String id = UUID.randomUUID().toString().substring(0, 5);
////            list.add(id);
////        }

        // 多线程操作
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                String id = UUID.randomUUID().toString().substring(0, 5);
                list.add(id);
                countDownLatch.countDown();
            }).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        list.forEach(System.out::println);
        System.out.println(list.size());
    }
}
