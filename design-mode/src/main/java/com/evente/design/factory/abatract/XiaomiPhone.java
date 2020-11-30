package com.evente.design.factory.abatract;

/**
 * @author : Kasa
 * @date : 2020/11/30 18:02
 * @descripthon : 小米手机产品
 */
public class XiaomiPhone implements IPhoneProduct {
    @Override
    public void start() {
        System.out.println("开启小米手机");
    }

    @Override
    public void shutdown() {
        System.out.println("关闭小米手机");
    }

    @Override
    public void call() {
        System.out.println("小米打电话");
    }

    @Override
    public void sendSMS() {
        System.out.println("小米发短信");
    }
}
