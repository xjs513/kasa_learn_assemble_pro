package com.kuang_juc.lock8;

import java.util.concurrent.TimeUnit;


/**
 * 5. 增加两个静态同步方法,只有一个对象
 * 6. 两个对象，两个同步方法，先调用哪个？？
 */
public class Test3 {
    public static void main(String[] args) {
        Phone3 phone = new Phone3();


        for (int i = 0; i < 100; i++) {
            new Thread(Phone3::call).start();
            new Thread(Phone3::sendSms).start();
        }

    }
}

class Phone3 {

    // static synchronized 锁的对象是 Class 类模板
    // 俩方法的锁相同，谁先拿到谁先执行~！
    public static synchronized void sendSms(){
        try {
            TimeUnit.SECONDS.sleep(4L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    public static synchronized void call(){
        System.out.println("打电话");
    }

    public void hello(){
        System.out.println("Hello");
    }
}