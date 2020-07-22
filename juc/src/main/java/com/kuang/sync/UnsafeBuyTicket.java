package com.kuang.sync;

import java.util.concurrent.TimeUnit;

public class UnsafeBuyTicket {
    public static void main(String[] args) {
        BuyTicket station = new BuyTicket();
        new Thread(station, "苦逼的我").start();
        new Thread(station, "牛逼的你").start();
        new Thread(station, "可恶的牛").start();
    }
}

class BuyTicket implements Runnable {

    private int ticketNum = 10;
    private boolean flag = true;

    @Override
    public void run() {
        // buy ticket
        while (flag){
            buy();
        }
    }

    private synchronized void buy(){
        // if available
        if (ticketNum <=0){
            flag = false;
            return;
        }
        // delay a while
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() +
            "===> 拿到了第[" + ticketNum-- + "]张票"
        );
    }
}