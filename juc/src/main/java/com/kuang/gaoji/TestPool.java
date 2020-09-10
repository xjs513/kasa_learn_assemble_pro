package com.kuang.gaoji;

import java.util.concurrent.*;

public class TestPool {
    public static void main(String[] args) {
        //new Thread(new MyRunnable(), "KKK").start();

        // 1. 创建服务
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        //Future<?> future = executorService.submit(new MyRunnable());
        //System.out.println("future = " + future);
//        executorService.execute(new MyRunnable());
//        executorService.execute(new MyRunnable());
//        executorService.execute(new MyRunnable());
//        executorService.execute(new MyRunnable());

        for (int i = 0; i < 30; i++) {
            executorService.execute(new MyRunnable());
        }

//        Future<Integer> future = executorService.submit(new MyCallable());
//        System.out.println("future.isDone() = " + future.isDone());
//        if (future.isDone()){
//            try {
//                System.out.println(future.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//        try {
//            System.out.println(future.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        executorService.shutdown();
    }
}

class MyRunnable implements Runnable{
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("name = " + name);
    }
}

class MyCallable implements Callable<Integer>{
    @Override
    public Integer call() {
        String name = Thread.currentThread().getName();
        System.out.println("name = " + name);
        String str = "1234556";
        return Integer.parseInt(str);
    }
}
