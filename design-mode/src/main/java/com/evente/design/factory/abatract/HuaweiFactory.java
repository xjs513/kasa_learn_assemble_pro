package com.evente.design.factory.abatract;

/**
 * @author : Kasa
 * @date : 2020/11/30 18:12
 * @descripthon : 华为工厂类
 */
public class HuaweiFactory implements IProductFactory {
    @Override
    public IPhoneProduct phoneProduct() {
        return new HuaweiPhone();
    }

    @Override
    public IRouterProduct routerProduct() {
        return new HuaweiRouter();
    }
}
