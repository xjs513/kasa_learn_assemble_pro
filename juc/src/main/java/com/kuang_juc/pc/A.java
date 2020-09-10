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
 */
public class A {
    public static void main(String[] args) {

        LockData data = new LockData();

        int  top =  10;

        new Thread(()->{
            for (int i = 0; i < top; i++) {
                data.increment();
            }
        }, "A").start();
        new Thread(()->{
            for (int i = 0; i < top; i++) {
                data.decrement();
            }
        }, "B").start();

        new Thread(()->{
            for (int i = 0; i < top; i++) {
                data.increment();
            }
        }, "AA").start();

        new Thread(()->{
            for (int i = 0; i < top; i++) {
                data.decrement();
            }
        }, "BB").start();
    }
}
