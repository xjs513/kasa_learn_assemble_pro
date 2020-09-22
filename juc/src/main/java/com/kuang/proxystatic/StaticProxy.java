package com.kuang.proxystatic;

public class StaticProxy {
    public static void main(String[] args) {
        WeddingCompany weddingCompany = new WeddingCompany(new You());
        weddingCompany.happyMarry();

        // 对比线程的创建方式
        Thread thread = new Thread(() -> {
            System.out.println("create an empty thread.");
        });
        thread.start();
    }
}
