package com.kuang.model;

public class Resources {
    private int num = 0;

    public synchronized void increment(int i) {
        while (0 != num) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.num += i;
        System.out.println(Thread.currentThread().getName() + "[num = " + num + "]");
        this.notifyAll();
    }

    public synchronized void increment() {
        this.increment(1);
    }

    public synchronized void decrement(int i) {
        while (0 == num) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.num -= i;
        System.out.println(Thread.currentThread().getName() + "[num = " + num + "]");
        this.notifyAll();
    }

    public synchronized void decrement() {
        this.decrement(1);
    }
}
