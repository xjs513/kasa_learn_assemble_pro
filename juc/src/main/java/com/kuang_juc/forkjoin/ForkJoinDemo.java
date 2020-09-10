package com.kuang_juc.forkjoin;

/**
 * 求和计算的任务！
 * 3000
 * 6000 ForkJoin
 * 9000 Stream 并行流
 */
public class ForkJoinDemo {

    private Long start;
    private Long end;

    // 临界值
    private Long temp = 10000L;

    public ForkJoinDemo(Long start, Long end) {
        this.start = start;
        this.end = end;
    }


    public void test(){
        if (end - start > temp) {
            // ForkJoin
        } else {
            // common calculate
            int sum = 0;
            for (int i = 0; i < 10_0000_0000; i++) {
                sum += i;
            }
            System.out.println("sum = " + sum);
        }
    }

    public static void main(String[] args) {




    }
}
