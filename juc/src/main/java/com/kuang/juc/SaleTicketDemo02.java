package com.kuang.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基本的卖票例子
 * 真正的多线程开发
 * 线程就是一个单独的资源类，没有任何的附属操作
 * 1. 属性 方法
 */
public class SaleTicketDemo02 {
    public static void main(String[] args) {
        // 并发就是多个线程操作同一个资源类
        // 把资源类丢入线程就可以了
        Ticket2 ticket = new Ticket2();

        new Thread(() ->  {
            for (int i = 0; i < 60; i++) {
                if (ticket.canSale())
                    ticket.sale();
                else {
                    System.out.println("A 发现售罄，售票完毕！~");
                    break;
                }
            }
        }, "A").start();
        new Thread(() ->  {
            for (int i = 0; i < 60; i++) {
                if (ticket.canSale())
                    ticket.sale();
                else {
                    System.out.println("B 发现售罄，售票完毕！~");
                    break;
                }
            }
        }, "B").start();
        new Thread(() ->  {
            for (int i = 0; i < 60; i++) {
                if (ticket.canSale())
                    ticket.sale();
                else {
                    System.out.println("C 发现售罄，售票完毕！~");
                    break;
                }
            }
        }, "C").start();
    }
}


// 资源类  OOP
class Ticket2 {
    // 属性 方法
    private int number = 50;

    Lock lock = new ReentrantLock();

    public boolean canSale(){
        return number>0;
    }

    // 卖票的方法
    // synchronized 的本质： 队列  锁
    // 锁 对象或 Class

    public void sale(){
        lock.lock();
        try {
            if (number>0){
                String str = Thread.currentThread().getName() +
                        "卖出了" + number-- + "票, 剩余：" + number;
                System.out.println(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}