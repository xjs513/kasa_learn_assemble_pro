package com.bilibili.threadlocal;


// https://www.bilibili.com/video/BV1N741127FH?p=6
public class Test_03 {

    private static ThreadLocal<String> tl = new ThreadLocal<>();

    public String getContent() {
        return tl.get();
    }

    public void setContent(String content) {
        tl.set(content);
    }

    public void release() {
        tl.remove();
    }

    public static void main(String[] args) {
        Test_03 demo = new Test_03();
        for (int i = 0; i < 5; i++) {
            int a = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String name = Thread.currentThread().getName();
                    demo.setContent(name + "的数据");
                    System.out.println("----------------------------");
                    System.out.println(name + "--------->" + demo.getContent());
                    demo.release(); // 解绑 防止内存泄露
                }
            });

            thread.setName("线程" + i);
            thread.start();

        }
    }

}
