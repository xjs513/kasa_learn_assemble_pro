package com.kuang.entrance;

import com.kuang.model.Resources;

public class JUCTest01 {




    public static void main(String[] args) {
        Resources resources = new Resources();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> resources.increment(), "A").start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(() -> resources.decrement(), "B").start();
        }
    }

}
