package com.kuang_juc.unsafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * java.util.ConcurrentModificationException
 *
 */
public class HashMapTest {
    public static void main(String[] args) {
        // HashMap 是这样用的么   ?  不是 工作中不用 HashMap
        // HashMap 默认等价于什么 ?  new HashMap<>(16, 0.75f)
//        Map<String, String> map = new HashMap<>(16, 0.75f);
        // 解决方案1：
//        Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
        // 解决方案2：
         Map<String, String> map = new ConcurrentHashMap<>();
        // 解决方案3：
        // Map<String, String> map =
        int num = 30;

        // 并发下 HashMap 不安全
        // 多线程操作
        for (int i = 0; i < num; i++) {
            new Thread(() -> {
                String id = UUID.randomUUID().toString().substring(0, 5);
                map.put(id, id);
                System.out.println(map);
            }).start();
        }
    }
}
