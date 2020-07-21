package com.kuang.proxystatic;


// 代理角色
public class WeddingCompany implements Marry {

    private Marry target;

    public WeddingCompany(Marry target) {
        this.target = target;
    }

    @Override
    public void happyMarry() {
        before();
        this.target.happyMarry();
        after();
    }

    private void after() {
        System.out.println("结婚前，布置现场");
    }

    private void before() {
        System.out.println("结婚后，收尾款!!");
    }
}
