package com.evente.algorithm.test;

import java.util.*;

public class HashMapTest {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("1", "a1");
        map.put("2", "a2");
        map.put("3", "a3");
        map.put("4", "a4");


        List<String> list = new ArrayList<>(4);
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (next.equals("1"))
                iterator.remove();
            iterator.forEachRemaining(s -> System.out.println("s = " + s));
        }


        System.out.println(list.size());

//        while (iterator.hasNext()) {
//            String next = iterator.next();
//            System.out.println(next);
//        }
    }
}
