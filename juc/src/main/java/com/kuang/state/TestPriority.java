package com.kuang.state;

public class TestPriority {
    public static void main(String[] args) {
        System.out.println(
                Thread.currentThread().getName() +
                        "=>" +
                        Thread.currentThread().getPriority()
        );

        MyPriority myPriority = new MyPriority();

        Thread t1 = new Thread(myPriority, "A1");
        Thread t2 = new Thread(myPriority, "A2");
        Thread t3 = new Thread(myPriority, "A3");
        Thread t4 = new Thread(myPriority, "A4");
        Thread t5 = new Thread(myPriority, "A5");
        Thread t6 = new Thread(myPriority, "A6");

        t1.start();

        t2.setPriority(1);
        t2.start();

        t3.setPriority(4);
        t3.start();

        t4.setPriority(10);
        t4.start();

        t5.setPriority(8);
        t5.start();

        t6.setPriority(7);
        t6.start();
    }
}



class MyPriority implements Runnable{

    @Override
    public void run() {
        System.out.println(
            Thread.currentThread().getName() +
            "=>" +
            Thread.currentThread().getPriority()
        );
    }
}