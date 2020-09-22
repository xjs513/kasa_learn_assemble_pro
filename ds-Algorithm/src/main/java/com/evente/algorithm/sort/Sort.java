package com.evente.algorithm.sort;

import java.util.Arrays;

/**
 * @author : Kasa
 * @date : 2020/9/22 13:43
 * @descripthon :
 */
public class Sort {

    public static void main(String[] args) {
        int[] ints = {3, 5, 8, 1, 2, 9, 4, 7, 6};
        System.out.println(Arrays.toString(ints));

        // bubbleSort(ints);
        // System.out.println(Arrays.toString(ints));

        // chooseSort(ints);
        // System.out.println(Arrays.toString(ints));

        insertSort(ints);
        System.out.println(Arrays.toString(ints));
    }

    // 冒泡排序
    private static void bubbleSort(int[] ints){
        int len = ints.length;
        for (int i = 0; i < len-1; i++) {
            for (int j = 0; j < len -1 - i; j++) {
                if (ints[j] > ints[j+1]){
                    swap(ints, j, j+1);
                }
            }
        }
    }

    // 选择排序
    private static void chooseSort(int[] ints){
        int len = ints.length;
        for (int i=1;i<len;i++){
            int minIndex = i;
            for (int j=i;j<len;j++){
                if (ints[j] < ints[minIndex])
                    minIndex = j;
            }
            //  System.out.println("minIndex = " + minIndex);
            if (ints[minIndex] < ints[i-1]){
                swap(ints, minIndex, i-1);
            }

        }
    }

    // 插入排序
    private static void insertSort(int[] ints){
        int len = ints.length;
        for (int i=0;i<len;i++){
            for (int j = i; j > 0; j--) {
                if (ints[j] < ints[j-1]){
                    swap(ints, j, j-1);
                }
            }
        }
    }

    // 堆排序

    // 归并排序

    // 快速排序


    private static void swap(int[] ints, int i, int j){
        int tmp = ints[i];
        ints[i] = ints[j];
        ints[j] = tmp;
    }
}
