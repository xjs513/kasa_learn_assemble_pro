package com.evente.algorithm.test;

import com.evente.algorithm.service.List;
import com.evente.algorithm.service.impl.LinkedList;

public class LinkedListDemo {

    public static void main(String[] args) {
        List<String> list = new LinkedList<>();

        list.shift("5");
        list.shift("4");
        list.shift("3");
        list.shift("2");
        list.shift("1");

//        list.print();


//        System.out.println(list.contains("3"));
//        System.out.println(list.contains("4"));
//        System.out.println(list.contains("41"));
//
//        System.out.println(list.indexOf("3"));
//        System.out.println(list.indexOf("0"));
//        System.out.println(list.indexOf("1"));
//        System.out.println(list.indexOf("5"));
//        System.out.println(list.indexOf("31"));




        //list.insert(0, "x");
        //list.insert(6, "x");
//        list.insert(3, "x");
//
//        list.insert(1, "ZZ");

//        list.print();



//        list.insertBefore("1", "before 1");
//        list.insertBefore("ZZ", "before ZZ");
//        list.insertBefore("99", "before 99");

//        System.out.println(list.indexOf("x"));
//        list.insertAfter("x", "after x");
//
//        list.insertAfter("5", "after 5");

//        list.insert(5, "after x");

//        list.print();
//
//
//        System.out.println(list.get(3));
//
//        System.out.println(list.replace(5, "6666"));

        list.print();

        list.reverse();

        list.print();

        System.out.println(list.getSize());

    }

}
