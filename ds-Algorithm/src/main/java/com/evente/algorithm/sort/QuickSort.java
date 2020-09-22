package com.evente.algorithm.sort;

import java.util.Arrays;

/**
 * @author : Kasa
 * @date : 2020/9/22 15:27
 * @descripthon :
 *
 * https://blog.csdn.net/ljb825802164/article/details/106024058
 */
public class QuickSort {
    public static void main(String[] args) {
        int [] nums = {6,1,2,5,9,3,4,7,10,8};
        quickSort(nums);
        for (int num : nums){
            System.out.print(num +" ");
        }
    }
    // 快速排序，a是数组，n表示数组的大小
    public static void quickSort(int[] a) {
        int n = a.length;
        quickSortInternally(a, 0, n-1);
    }


    // 快速排序递归函数，p,r为下标
    private static void quickSortInternally(int[] a, int p, int r) {
        if (p >= r) return;

        int q = partition(a, p, r); // 获取分区点
        quickSortInternally(a, p, q-1);
        quickSortInternally(a, q+1, r);
    }

    // {6,1,2,5,9,3,4,7,10,8} p=0  r=9
    private static int partition(int[] a, int p, int r) {
        // todo : 记录中位数的值
        // todo : 两个哨兵移动先后没有区别，因为两个哨兵在同一起点。
        int pivot = a[r]; //  =8
        // todo : 记录中位数的下标
        int i = p;// =0
        for(int j = p; j < r; ++j) {
            if (a[j] < pivot) {
                if (i == j) {
                    System.out.println("==");
                    ++i;
                } else {
                    System.out.println("~~");
                    int tmp = a[i];
                    a[i++] = a[j];
                    a[j] = tmp;
                }
            }
        }
        int tmp = a[i];
        a[i] = a[r];
        a[r] = tmp;
        return i;
    }

}
