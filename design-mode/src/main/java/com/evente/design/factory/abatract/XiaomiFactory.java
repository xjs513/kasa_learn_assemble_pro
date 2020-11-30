package com.evente.design.factory.abatract;

/**
 * @author : Kasa
 * @date : 2020/11/30 18:12
 * @descripthon : 小米工厂类
 */
public class XiaomiFactory implements IProductFactory {
    @Override
    public IPhoneProduct phoneProduct() {
        return new XiaomiPhone();
    }

    @Override
    public IRouterProduct routerProduct() {
        return new XiaomiRouter();
    }
}
