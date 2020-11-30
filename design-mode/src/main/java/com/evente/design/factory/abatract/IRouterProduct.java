package com.evente.design.factory.abatract;

/**
 * @author : Kasa
 * @date : 2020/11/30 17:59
 * @descripthon : 路由器产品接口
 */
public interface IRouterProduct {
    void start();
    void shutdown();
    void openWifi();
    void setting();
}
