package com.evente.design.factory.abatract;

/**
 * @author : Kasa
 * @date : 2020/11/30 17:59
 * @descripthon : 手机产品接口
 */
public interface IPhoneProduct {
    void start();
    void shutdown();
    void call();
    void sendSMS();
}
