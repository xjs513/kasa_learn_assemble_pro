package com.kuang_juc.lock8;

import java.util.concurrent.TimeUnit;


/**
 * 7. 一个静态同步方法， 一个普通同步方法 一个对象，先调用哪个？ 打电话
 * 8. 一个静态同步方法， 一个普通同步方法 一个对象，先调用哪个？
 */
public class Test4 {
    public static void main(String[] args) {
        Phone4 phone1 = new Phone4();
        Phone4 phone2 = new Phone4();

        new Thread(Phone4::sendSms).start();
        new Thread(phone2::call).start();

    }
}

class Phone4 {

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

    // synchronized 锁的对象是方法的调用者
    public synchronized void call(){
        System.out.println("打电话");
    }

    public void hello(){
        System.out.println("Hello");
    }
}