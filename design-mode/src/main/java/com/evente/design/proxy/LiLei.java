package com.evente.design.proxy;

/**
 * @author : Kasa
 * @date : 2020/9/22 13:28
 * @descripthon :
 */
public class LiLei {
    public static void main(String[] args) {
        Girl girl = new WangMeiLi();
        WangMeiLiProxy family = new WangMeiLiProxy(girl);
        Girl mother = (Girl)family.getProxyInstance();
        mother.date();
    }
}
