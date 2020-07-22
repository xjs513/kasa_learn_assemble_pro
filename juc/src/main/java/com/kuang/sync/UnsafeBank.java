package com.kuang.sync;

import java.util.concurrent.TimeUnit;

// 不安全的取钱
// 两个人同时从一个账户取钱
public class UnsafeBank {
    public static void main(String[] args) {
        // 账户
        Account account = new Account(100, "结婚基金");

        Drawing you = new Drawing(account, 50, "你");
        Drawing wife = new Drawing(account, 100, "她");

        you.start();
        wife.start();
    }
}


class Account{
    private int money; // 余额
    private String name; // 卡名

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account(int money, String name) {
        this.money = money;
        this.name = name;
    }
}

class Drawing extends Thread{
    private Account account; // 账户
    private int drawingMoney;// 取了多少钱
    private int nowMoney;    // 你手里有多少钱

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public int getDrawingMoney() {
        return drawingMoney;
    }

    public void setDrawingMoney(int drawingMoney) {
        this.drawingMoney = drawingMoney;
    }

    public int getNowMoney() {
        return nowMoney;
    }

    public void setNowMoney(int nowMoney) {
        this.nowMoney = nowMoney;
    }

    public Drawing(Account account, int drawingMoney, String name) {
        super(name);
        this.account = account;
        this.drawingMoney = drawingMoney;
    }

    @Override
    public void run() {
        synchronized (account){
            System.out.println(this.getName() + "=>取钱之前卡内余额为 = " + account.getMoney());
            System.out.println(this.getName() + "=>取钱要支取的金额为 = " + this.getDrawingMoney());
            if (account.getMoney() - this.getDrawingMoney() < 0){
                System.out.println(this.getName() + "余额不足！~");
                System.out.println(this.getName() + "余额 = " + account.getMoney());
                System.out.println(this.getName() + "支取 = " + this.getDrawingMoney());
                return;
            }
            // 模拟延时
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 卡内余额
            account.setMoney(account.getMoney() - this.getDrawingMoney());
            // 你手里的钱
            this.setNowMoney(this.getNowMoney() + this.getDrawingMoney());

            System.out.println(this.getName() + "=>取钱之后卡内余额为 = " + account.getMoney());
            System.out.println(this.getName() + "=>取钱之后手中现金为 = " + this.getNowMoney());
        }
    }
}