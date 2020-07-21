package com.kuang.demo01;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestThread4 implements Runnable{

    private AtomicInteger ticketNum = new AtomicInteger(10);

    @Override
    public void run() {
//        while (true){
//            if (ticketNum.intValue() <= 0)
//                break;
//
//            // 模拟延时, 放大问题
//            try {
//                TimeUnit.SECONDS.sleep(1L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println(Thread.currentThread().getName() +
//                "===> 拿到了第[" + ticketNum.getAndDecrement() + "]张票"
//            );
//        }
        while (ticketNum.intValue() > 0){
            int currentValue = ticketNum.getAndDecrement();
            // 模拟延时, 放大问题
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("当前 ticketNum = " +
                    currentValue + ", " +
                    Thread.currentThread().getName() +
                    "===> 拿到了第[" + currentValue + "]张票"
            );
        }
    }

    public static void main(String[] args){
        TestThread4 ticket = new TestThread4();

        new Thread(ticket, "小明").start();
        new Thread(ticket, "老师").start();
        new Thread(ticket, "黄牛党").start();

    }

}
