package com.kuang_juc.bq;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ArrayBlockingQueueDemo {
    public static void main(String[] args) {
        test4();
    }

    /**
     * throw exception
     */
    static void test1(){
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        // java.lang.IllegalStateException: Queue full
        // System.out.println(blockingQueue.add("c"));

        System.out.println(blockingQueue.element());

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        // java.util.NoSuchElementException
        // System.out.println(blockingQueue.remove());
    }

    /**
     * 不抛异常
     */
    static void test2(){
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer(null));
        // return false
        // System.out.println(blockingQueue.offer("d"));


        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());

    }

    /**
     * 一直阻塞
     */
    static void test3(){
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);


        try {
            blockingQueue.put("a");
            blockingQueue.put("b");
            blockingQueue.put("c");

            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
            // System.out.println(blockingQueue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }


    /**
     * 超时阻塞
     */
    static void test4(){
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);


        try {
            blockingQueue.offer("a", 1, TimeUnit.SECONDS);
            blockingQueue.offer("b", 1, TimeUnit.SECONDS);
            blockingQueue.offer("c", 1, TimeUnit.SECONDS);
            blockingQueue.offer("d", 3, TimeUnit.SECONDS);


            System.out.println(blockingQueue.poll(3, TimeUnit.SECONDS));
            System.out.println(blockingQueue.poll(3, TimeUnit.SECONDS));
            System.out.println(blockingQueue.poll(3, TimeUnit.SECONDS));
            System.out.println(blockingQueue.poll(3, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
