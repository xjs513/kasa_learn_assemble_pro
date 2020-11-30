package com.evente.design.factory.abatract;

/**
 * @author : Kasa
 * @date : 2020/11/30 18:09
 * @descripthon : 抽象产品工厂，定义具体工厂的动作
 */
public interface IProductFactory {

    // 生产手机
    IPhoneProduct phoneProduct();
    // 生产路由器
    IRouterProduct routerProduct();
}
