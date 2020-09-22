package com.evente.design.proxy;

/**
 * @author : Kasa
 * @date : 2020/9/22 13:22
 * @descripthon :
 */
public class WangMeiLi implements Girl {
    @Override
    public void date() {
        System.out.println("约会");
    }

    @Override
    public void watchMovie() {
        System.out.println("看电影");
    }
}
