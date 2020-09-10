package com.bilibili.threadlocal;

public class Test_02 {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static void main(String[] args) {
        Test_02 demo = new Test_02();
        for (int i = 0; i < 5; i++) {
            int a = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (Test_02.class) {
                        String name = Thread.currentThread().getName();
                        demo.setContent(name + "的数据");
                        System.out.println("----------------------------");
                        System.out.println(name + "--------->" + demo.getContent());
                    }
                }
            });

            thread.setName("线程" + i);
            thread.start();

        }
    }

}
