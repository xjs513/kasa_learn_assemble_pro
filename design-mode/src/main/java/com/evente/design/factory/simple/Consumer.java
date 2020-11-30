package com.evente.design.factory.simple;

/**
 * @author : Kasa
 * @date : 2020/11/30 16:37
 * @descripthon :  静态工厂, 简单工厂模式
 */
public class Consumer {
    public static void main(String[] args) {
//        Car car1 = new Wuling();
//        Car car2 = new Tesla();


        Car car1 = CarFactory.getCar("五菱");
//        Car car2 = CarFactory.getCar("特斯拉");
        Car car2 = CarFactory.getCar("1特斯拉");

        assert car1 != null;
        car1.name();
        assert car2 != null;
        car2.name();
    }
}
