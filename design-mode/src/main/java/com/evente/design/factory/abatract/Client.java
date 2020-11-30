package com.evente.design.factory.abatract;

/**
 * @author : Kasa
 * @date : 2020/11/30 18:15
 * @descripthon :
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("============小米系列产品==============");

        IProductFactory xiaomiFactory = new XiaomiFactory();

        IPhoneProduct phoneProduct = xiaomiFactory.phoneProduct();
        phoneProduct.call();
        phoneProduct.sendSMS();

        IRouterProduct routerProduct = xiaomiFactory.routerProduct();
        routerProduct.openWifi();
        routerProduct.setting();


        System.out.println("==============华为系列产品==============");

        IProductFactory huaweiFactory = new HuaweiFactory();

        phoneProduct = huaweiFactory.phoneProduct();
        phoneProduct.call();
        phoneProduct.sendSMS();

        routerProduct = huaweiFactory.routerProduct();
        routerProduct.openWifi();
        routerProduct.setting();
    }
}
