package com.kuang_juc.pc;


/**
 * 线程间的通信问题：生产者和消费者问题
 * A B操作同一个变量 num = 0
 * A: num + 1
 * B: num - 1
 *
 * 线程操作资源类
 *
 * 资源类 独立耦合的
 *
 * 判断等待  干活 通知
 * Condition 精准的通知唤醒线程
 * A => B => C 一次执行
 */
public class C {
    public static void main(String[] args) {
        LockDataOneByOne data = new LockDataOneByOne();

        int  top =  10;

        new Thread(()->{
            for (int i = 0; i < top; i++) {
                data.printA();
            }
        }, "A").start();
        new Thread(()->{
            for (int i = 0; i < top; i++) {
                data.printB();
            }
        }, "B").start();

        new Thread(()->{
            for (int i = 0; i < top; i++) {
                data.printC();
            }
        }, "C").start();
    }

    // 生产线： 下单 -》 支付 -》 交易 -》 物流
}
