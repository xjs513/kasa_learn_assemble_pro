//package com.evente.algorithm.service.impl;
//
//import com.evente.algorithm.service.List;
//import com.evente.algorithm.service.Strategy;
//
//import java.util.Arrays;
//
//public class ListArray implements List<String> {
//    private String[] data;
//    private int size;
//    private final int DEFAULT_LEN = 8;
//    private Strategy<String> strategy;
//
//    public ListArray() {
//        this.size = this.DEFAULT_LEN;
//        this.data = new String[this.size];
//        this.strategy = new Strategy<String>() {
//            @Override
//            public boolean equals(String e1, String e2) {
//                return e1.equals(e2);
//            }
//
//            @Override
//            public int compare(String e1, String e2) {
//                return e1.compareTo(e2);
//            }
//        };
//    }
//
//
//    public ListArray(int size) {
//        this.size = size;
//        this.data = new String[this.size];
//        this.strategy = new Strategy<String>() {
//            @Override
//            public boolean equals(String e1, String e2) {
//                return e1.equals(e2);
//            }
//
//            @Override
//            public int compare(String e1, String e2) {
//                return e1.compareTo(e2);
//            }
//        };
//    }
//
//
//
//    @Override
//    public int getSize() {
//        return this.size;
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return this.size == 0;
//    }
//
//    @Override
//    public boolean contains(String s) {
//        return Arrays.stream(data).anyMatch(ele -> (strategy.equals(ele, s)));
//    }
//
//    @Override
//    public int indexOf(String s) {
//        for (int i = 0; i < this.size; i++) {
//            if ( strategy.equals(data[i], s) )
//                return i;
//        }
//        return -1;
//    }
//
//    @Override
//    public void insert(int i, String s) {
//        if (i<0 || i>=this.size)
//            throw new ArrayIndexOutOfBoundsException("");
//    }
//
//    @Override
//    public boolean insertBefore(String ojb, String s) {
//        return false;
//    }
//
//    @Override
//    public boolean insertAfter(String ojb, String s) {
//        return false;
//    }
//
//    @Override
//    public String remove(int i) throws IndexOutOfBoundsException {
//        return null;
//    }
//
//    @Override
//    public boolean remove(String s) {
//        return false;
//    }
//
//    @Override
//    public String replace(int i, String s) throws IndexOutOfBoundsException {
//        return null;
//    }
//
//    @Override
//    public String get(int i) throws IndexOutOfBoundsException {
//        return null;
//    }
//
//    public void shift(T t){
//
//    }
//
//    @Override
//    public void print() {
//
//    }
//}
