package com.kuang.juc;

/**
 * 基本的卖票例子
 * 真正的多线程开发
 * 线程就是一个单独的资源类，没有任何的附属操作
 * 1. 属性 方法
 */
public class SaleTicketDemo01 {
    public static void main(String[] args) {
        // 并发就是多个线程操作同一个资源类
        // 把资源类丢入线程就可以了
        Ticket ticket = new Ticket();

        new Thread(() ->  {
            for (int i = 0; i < 6; i++) {
                ticket.sale();
            }
        }, "A").start();
        new Thread(() ->  {
            for (int i = 0; i < 6; i++) {
                ticket.sale();
            }
        }, "B").start();
        new Thread(() ->  {
            for (int i = 0; i < 6; i++) {
                ticket.sale();
            }
        }, "C").start();
    }
}


// 资源类  OOP
class Ticket {
    // 属性 方法
    private int number = 15;

    // 卖票的方法
    // synchronized 的本质： 队列  锁
    // 锁 对象或 Class

    public synchronized void sale(){
        if (number>0){
            String str = Thread.currentThread().getName() +
                    "卖出了" + number-- + "票, 剩余：" + number;
            System.out.println(str);
        } else {
            String str = Thread.currentThread().getName() +
                    " 发现售罄，售票完毕！~";
            System.out.println(str);
        }
    }
}