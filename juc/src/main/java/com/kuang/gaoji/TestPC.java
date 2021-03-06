//package com.kuang.gaoji;
//
//
///**
// * 测试生产者消费者模型
// * 利用缓冲区解决：管程法
// *
// * 生产者 消费者 产品 缓冲区
// */
//public class TestPC {
//    public static void main(String[] args) {
//        SyncContainer container = new SyncContainer();
//        new Productor(container).start();
//        new Consumer(container).start();
//    }
//}
//
//// 生产者
//class Productor extends Thread{
//    SyncContainer container;
//
//    public Productor(SyncContainer container) {
//        this.container = container;
//    }
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 100; i++) {
//            container.push(new Chicken(i));
//            System.out.println("生产了[" + i + "]只鸡..");
//        }
//    }
//}
//// 消费者
//class Consumer extends Thread{
//    SyncContainer container;
//
//    public Consumer(SyncContainer container) {
//        this.container = container;
//    }
//
//    @Override
//    public void run() {
//        for (int i = 0; i < 100; i++) {
//            System.out.println("消费了[" + container.pop().id + "]只鸡..");
//        }
//    }
//}
//// 产品
//class Chicken {
//    int id; // 产品编号
//
//    public Chicken(int id) {
//        this.id = id;
//    }
//}
//// 缓冲区
//class SyncContainer{
//    // 定义缓冲区大小
//    Chicken[] chickens = new Chicken[10];
//    // 容器计数器
//    int count = 0;
//
//    // 生产者放入产品
//    public synchronized void push(Chicken chicken){
//        // 如果容器满了，则等待消费
//        if (count == chickens.length){
//            // 生产暂停，通知消费者消费
//            try {
//                this.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        chickens[count] = chicken;
//        count++;
//
//        // 通知消费者消费
//        this.notifyAll();
//    }
//
//    // 消费者消费产品
//    public synchronized Chicken pop(){
//        // 如果容器满了，则等待消费
//        if (count == 0){
//            // 消费暂停，通知消费者消费
//            try {
//                this.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        // 如果可以消费
//        count--;
//        Chicken chicken = chickens[count];
//
//        // 通知消费者消费
//        this.notifyAll();
//        return chicken;
//
//    }
//}