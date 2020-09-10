package com.kuang_juc.unsafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;

/**
 * java.util.ConcurrentModificationException
 */
public class SetTest {
    public static void main(String[] args) {
        int num = 30;
        // 并发下 HashSet 不安全
        // Set<String> set = new HashSet<>();
        // 解决方案1：
        // Set<String> set = Collections.synchronizedSet(new HashSet<>());
        // 解决方案2：
        Set<String> set = new CopyOnWriteArraySet<>();

        // 多线程操作
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                String id = UUID.randomUUID().toString().substring(0, 5);
                set.add(id);
                System.out.println(set);
            }).start();
        }
    }
}
