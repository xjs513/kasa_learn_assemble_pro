package com.kuang_juc.lock8;

import java.util.concurrent.TimeUnit;


/**
 * 1. 正常情况下 先执行哪个？              发短信
 * 2. sendSms 延迟4秒情况下 先执行哪个？   发短信
 *
 */
public class Test1 {
    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(phone::call).start();
        new Thread(phone::sendSms).start();
    }
}

class Phone {

    // synchronized 锁的对象时方法的调用者
    // 俩方法的锁相同，谁先拿到谁先执行~！
    public synchronized void sendSms(){
        try {
            TimeUnit.SECONDS.sleep(4L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public synchronized void call(){
        System.out.println("打电话");
    }
}