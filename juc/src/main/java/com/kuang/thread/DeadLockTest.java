package com.kuang.thread;

import java.util.concurrent.TimeUnit;

// 口红
class Lipstick{

}

// 镜子
class Mirror{

}

public class DeadLockTest {
    public static void main(String[] args) {
        Makeup g1 = new Makeup(0, "灰姑凉");
        Makeup g2 = new Makeup(1, "白雪公主");
        g1.start();
        g2.start();
    }
}

class Makeup extends Thread{

    // 需要的资源只有一份，用 static 来保证
    static Lipstick lipstick = new Lipstick();
    static Mirror mirror = new Mirror();

    int choice; // 选择
    String girlName; // 化妆的人

    public Makeup(int choice, String girlName){
        this.choice = choice;
        this.girlName = girlName;
    }

    // 互相持有对方的锁，需要拿到对方的资源
    private void makeup() throws InterruptedException {
        if (choice ==0){
            // 锁定持有口红的锁
            synchronized (lipstick){
                System.out.println(this.girlName + "：获得了口红");
                TimeUnit.SECONDS.sleep(1L);
                synchronized (mirror){
                    System.out.println(this.girlName + "：获得了镜子");
                }
            }
        } else {
            // 锁定持有口红的锁
            synchronized (mirror){
                System.out.println(this.girlName + "：获得了镜子");
                TimeUnit.SECONDS.sleep(2L);
                synchronized (lipstick){
                    System.out.println(this.girlName + "：获得了口红");
                }
            }
        }
    }

    @Override
    public void run() {
        try {
            makeup();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}