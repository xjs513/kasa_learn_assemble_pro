package com.kuang.state;


import java.util.concurrent.TimeUnit;

// 测试礼让线程
// 礼让不一定成功，看 CPU 心情
public class TestYield {
    public static void main(String[] args) {
        MyYield myYield1 = new MyYield();
        MyYield myYield2 = new MyYield();

        new Thread(myYield1, "AA").start();
        new Thread(myYield2, "BB").start();

    }
}


class  MyYield implements Runnable{

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + "线程开始执行");
        // 礼让
        //Thread.yield();
        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + "线程停止执行");
    }
}