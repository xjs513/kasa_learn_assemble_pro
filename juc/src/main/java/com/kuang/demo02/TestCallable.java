package com.kuang.demo02;

import com.kuang.model.WebDownLoader;

import java.util.concurrent.*;

public class TestCallable implements Callable<Boolean> {

    private String url;
    private String name;


    public TestCallable(String url, String name){
        this.url = url;
        this.name = name;
    }

    @Override
    public Boolean call() throws Exception {
        WebDownLoader webDownLoader = new WebDownLoader();
        webDownLoader.downloader(this.url, this.name);
        System.out.println("下载了文件名为：[" + this.name + "]");
        return true;
    }

    public static void main(String[] args) {
        TestCallable tc1 = new TestCallable("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595316915601&di=8518c2871d161cefef0cf8f1f58dda15&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F56%2F12%2F01300000164151121576126282411.jpg", "1.jpg");
        TestCallable tc2 = new TestCallable("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595316915601&di=2f473f12b82a8c16e2150ef742b12201&imgtype=0&src=http%3A%2F%2Fa2.att.hudong.com%2F36%2F48%2F19300001357258133412489354717.jpg", "2.jpg");
        TestCallable tc3 = new TestCallable("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595316915601&di=0bf4f4c14d45f2bf00e971c5cc87aadf&imgtype=0&src=http%3A%2F%2Fa1.att.hudong.com%2F05%2F00%2F01300000194285122188000535877.jpg", "3.jpg");

        // 1. 创建执行任务
        ExecutorService ser = Executors.newFixedThreadPool(3);
        // 2. 提交执行
        Future<Boolean> result1 = ser.submit(tc1);
        Future<Boolean> result2 = ser.submit(tc2);
        Future<Boolean> result3 = ser.submit(tc3);
        // 3. 获取结果
        try {
            Boolean r1 = result1.get();
            Boolean r2 = result2.get();
            Boolean r3 = result3.get();

            System.out.println("r3 = " + r3);
            System.out.println("r2 = " + r2);
            System.out.println("r1 = " + r1);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        // 4. 关闭服务
        ser.shutdown();
    }
}
