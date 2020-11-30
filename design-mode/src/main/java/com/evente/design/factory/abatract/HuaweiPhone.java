package com.evente.design.factory.abatract;

/**
 * @author : Kasa
 * @date : 2020/11/30 18:02
 * @descripthon : 华为手机产品
 */
public class HuaweiPhone implements IPhoneProduct {
    @Override
    public void start() {
        System.out.println("开启华为手机");
    }

    @Override
    public void shutdown() {
        System.out.println("关闭华为手机");
    }

    @Override
    public void call() {
        System.out.println("华为打电话");
    }

    @Override
    public void sendSMS() {
        System.out.println("华为发短信");
    }
}
