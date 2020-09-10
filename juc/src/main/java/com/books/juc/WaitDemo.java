package com.books.juc;

import java.util.concurrent.TimeUnit;

public class WaitDemo {
    public static void main(String[] args) {
        // busyThread 不停的运行
        Thread busyThread = new Thread(new BusyRunner(),  "busyThread");
        // busyThread.setDaemon(true);
        busyThread.start();

        try {
            TimeUnit.SECONDS.sleep(10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            busyThread.wait(5000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    static class BusyRunner implements Runnable{

        @Override
        public void run() {
            while (true){
                System.out.println(System.currentTimeMillis());
                try {
                    TimeUnit.SECONDS.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
