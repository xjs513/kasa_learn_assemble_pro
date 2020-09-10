package com.kuang_juc.lock8;

import java.util.concurrent.TimeUnit;


/**
 * 3. 增加了普通方法 先发短信还是 hello ??
 * 4. 两个对象，两个同步方法，先调用哪个？？
 */
public class Test2 {
    public static void main(String[] args) {
        Phone2 phone1 = new Phone2();
        Phone2 phone2 = new Phone2();

        new Thread(phone1::sendSms).start();
        new Thread(phone2::call).start();
    }
}

class Phone2 {

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

    public void hello(){
        System.out.println("Hello");
    }
}