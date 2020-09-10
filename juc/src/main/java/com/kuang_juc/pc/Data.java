package com.kuang_juc.pc;


// 判断等待  干活 通知
public class Data {
    private int number  = 0;

    // +1
    public synchronized void increment()  {
        //  if 只判断一次，会有虚假唤醒的问题, 用 while
        while (number != 0){
            // 等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number++ ;
        System.out.println(Thread.currentThread().getName() + " => " + number);
        // 通知消费
        this.notifyAll();
    }

    // -1
    public synchronized void decrement()  {
        //  if 只判断一次，会有虚假唤醒的问题, 用 while
        while (number == 0){
            // 等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number-- ;
        System.out.println(Thread.currentThread().getName() + " => " + number);
        // 通知生产
        this.notifyAll();
    }
}
