package com.kuang.state;

public class TestDaemon {

    public static void main(String[] args) {
        God god = new God();
        Thread godThread = new Thread(god, "God_thread");
        godThread.setDaemon(true);

        godThread.start();


        You u = new You();
        new Thread(u, "You_thread").start();
    }

}

class God implements Runnable{

    @Override
    public void run() {
        while (true) {
            System.out.println("上帝保佑着你！！");
        }
    }
}


class You implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("好好加班干活！！");
        }
        System.out.println("== GoodBye World.. ==");
    }
}