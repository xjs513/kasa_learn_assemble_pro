package com.kuang.state;

public class TestStop implements Runnable {

    // 1. set a flag
    private boolean flag = true;

    @Override
    public void run() {
        int i = 0;
        while (flag) {
            System.out.println("run ... Thread" + i++);
        }
    }

    // 2. set a method to stop thread
    public void stop(){
        this.flag = false;
    }

    public static void main(String[] args) {
        TestStop testStop = new TestStop();

        new Thread(testStop).start();

        for (int i = 0; i < 1000; i++) {
            System.out.println("main i = " + i);
            if (i == 900){
                testStop.stop();
                System.out.println("线程该停止了");
            }
        }
    }

}
