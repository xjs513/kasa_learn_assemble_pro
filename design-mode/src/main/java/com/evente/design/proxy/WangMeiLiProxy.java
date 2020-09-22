package com.evente.design.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author : Kasa
 * @date : 2020/9/22 13:23
 * @descripthon :
 */
public class WangMeiLiProxy implements InvocationHandler {

    private Girl girl;

    WangMeiLiProxy(Girl girl) {
        this.girl = girl;
    }


    /**
     * 增强器，代理增强被代理对象的功能
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object ret = method.invoke(girl, args);
        after();
        return ret;
    }

    private void before(){
        System.out.println("代理前动作");
    }

    private void after(){
        System.out.println("代理后动作");
    }

    Object getProxyInstance(){
        return Proxy.newProxyInstance(girl.getClass().getClassLoader(),
                girl.getClass().getInterfaces(), this);
    }
}
