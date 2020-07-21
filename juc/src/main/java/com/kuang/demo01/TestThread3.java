package com.kuang.demo01;

import com.kuang.model.WebDownLoader;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TestThread3 implements Runnable{
    private String url;
    private String name;

    public TestThread3(String url, String name){
        this.url = url;
        this.name = name;
    }

    @Override
    public void run() {
        WebDownLoader webDownLoader = new WebDownLoader();
        webDownLoader.downloader(this.url, this.name);
        System.out.println("下载了文件名为：[" + this.name + "]");
    }

    public static void main(String[] args) {
        TestThread3 testThread30 = new TestThread3("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595316915601&di=8518c2871d161cefef0cf8f1f58dda15&imgtype=0&src=http%3A%2F%2Fa0.att.hudong.com%2F56%2F12%2F01300000164151121576126282411.jpg", "1.jpg");
        TestThread3 testThread31 = new TestThread3("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595316915601&di=2f473f12b82a8c16e2150ef742b12201&imgtype=0&src=http%3A%2F%2Fa2.att.hudong.com%2F36%2F48%2F19300001357258133412489354717.jpg", "2.jpg");
        TestThread3 testThread32 = new TestThread3("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595316915601&di=0bf4f4c14d45f2bf00e971c5cc87aadf&imgtype=0&src=http%3A%2F%2Fa1.att.hudong.com%2F05%2F00%2F01300000194285122188000535877.jpg", "3.jpg");

        new Thread(testThread30).start();
        new Thread(testThread31).start();
        new Thread(testThread32).start();
    }
}

