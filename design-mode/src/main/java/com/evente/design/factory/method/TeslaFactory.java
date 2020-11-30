package com.evente.design.factory.method;

/**
 * @author : Kasa
 * @date : 2020/11/30 17:16
 * @descripthon :
 */
public class TeslaFactory implements CarFactory{
    @Override
    public Car getCar() {
        return new Tesla();
    }
}
