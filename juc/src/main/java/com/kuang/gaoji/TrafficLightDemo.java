package com.kuang.gaoji;

public class TrafficLightDemo {
    public static void main(String[] args) {
        TrafficLight trafficLight = new TrafficLight();

        NSPassenger nsPassenger = new NSPassenger(trafficLight);
        EWPassenger ewPassenger = new EWPassenger(trafficLight);

        new Thread(nsPassenger, "NS").start();
        new Thread(ewPassenger, "EW").start();
    }
}




class TrafficLight{
    boolean canPassNS = true; // 南北方向信号灯状态

    // 南北通行
    synchronized void passCrossNS(int i){
        while (!canPassNS){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String name = Thread.currentThread().getName();
        System.out.println(
                name + ", canPassNS:[" + canPassNS + "], " +
                        "=南北=方向通过行人编号：[" + i + "]"
        );
        this.notifyAll(); // 通知东西走向通行
        this.canPassNS = !this.canPassNS;
    }

    // 东西通行
    synchronized void passCrossEW(int i){
        while (canPassNS){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        String name = Thread.currentThread().getName();
        System.out.println(
                name + ", canPassNS:[" + canPassNS + "], " +
                        "=东西=方向通过行人编号：[" + i + "]"
        );
        this.notifyAll(); // 通知南北走向通行
        this.canPassNS = !this.canPassNS;
    }
}

// 南北走向行人
class NSPassenger implements Runnable{
    TrafficLight trafficLight;
    public NSPassenger(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }
    @Override
    public void run() {
        for (int i = 1; i < 21; i++) {
            this.trafficLight.passCrossNS(i);
        }
    }
}

// 东西走向行人
class EWPassenger implements Runnable{
    TrafficLight trafficLight;

    public EWPassenger(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
    }

    @Override
    public void run() {
        for (int i =1; i < 21; i++) {
            this.trafficLight.passCrossEW(i);
        }
    }
}