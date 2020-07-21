package com.kuang.demo01;


import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// 模拟龟兔赛跑
public class Race implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i <= 100; i++) {

            // 模拟兔子睡觉
            if ("兔子".equals(Thread.currentThread().getName()) && i%10 ==0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                TimeUnit.MILLISECONDS.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 判断比赛是否结束
            boolean flag = gameOver(i);
            if (flag)
                break;
            System.out.println(
                Thread.currentThread().getName() +
                    "-->跑了" + i + "步"
            );
        }
    }

    private static String winner;

    // 判断比赛是否结束
    private boolean gameOver(int steps){
        // 已经有胜利者
        if (null != winner)
            return true;
        if (steps >= 100){
            System.out.println(
                    Thread.currentThread().getName() +
                            "到达终点！！！"
            );
            winner = Thread.currentThread().getName();
            System.out.println("winner is " + winner);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Race race = new Race();

        new Thread(race, "兔子").start();
        new Thread(race, "乌龟").start();


        Callable<Class<Void>> callable = Executors.callable(race, Void.class);
    }

}
