package com.kuang.state;

import java.util.concurrent.TimeUnit;

public class TestState {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("/////////");
        });

        // 观察状态
        Thread.State state = t.getState();
        System.out.println("state = " + state);

        t.start();
        state =  t.getState();
        System.out.println("state = " + state);

        // 只要线程未终止，就一直输出状态
        while (state != Thread.State.TERMINATED){
            try {
                TimeUnit.MILLISECONDS.sleep(1000L);
                state =  t.getState();
                System.out.println("state = " + state);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
