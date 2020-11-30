package com.evente.design.factory.method;

/**
 * @author : Kasa
 * @date : 2020/11/30 16:37
 * @descripthon : 工厂方法模式
 */
public class Consumer {
    public static void main(String[] args) {
        Car tesla = new TeslaFactory().getCar();
        Car wuLing = new WuLingFactory().getCar();

        tesla.name();
        wuLing.name();
    }
}
