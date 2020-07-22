package com.kuang.gaoji;

public class TestCommunication {
    public static void main(String[] args) {
        String str = "Hello, World.";
        System.out.println("str = " + str);
        synchronized (str) {
            try {
                str.wait(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
