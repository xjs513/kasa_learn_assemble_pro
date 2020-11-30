package com.evente.design.factory.simple;

/**
 * @author : Kasa
 * @date : 2020/11/30 16:39
 * @descripthon : 静态工厂, 简单工厂模式
 */
class CarFactory {
    static Car getCar(String car){
        if (car.equals("五菱"))
            return new WuLing();
        else if (car.equals("特斯拉"))
            return new Tesla();
        else
            return null;
    }
}
