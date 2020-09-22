package com.evente.algorithm.sort;

/**
 * @author : Kasa
 * @date : 2020/9/22 17:24
 * @descripthon :
 *
 * https://blog.csdn.net/ljb825802164/article/details/106024058
 */
public class QuickSort2 {
    public static void main(String[] args) {
        int [] nums = {6,1,2,5,9,3,4,7,10,8};
        quickSort(nums);
        for (int num : nums){
            System.out.print(num +" ");
        }
    }

    // 快速排序，a是数组，n表示数组的大小
    private static void quickSort(int[] a) {
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

    private static int partition(int[] a, int left, int right) {
        int i, j, t, temp;
        temp = a[left]; //temp中存的就是基准数
        i = left;
        j = right;
        while(i != j) { //顺序很重要，要先从右边开始找   i!=j表示两个指针未相遇
            while(a[j] >= temp && i < j)
                j--;
            while(a[i] <= temp && i < j)//再找右边的
                i++;
            if(i < j)//交换两个数在数组中的位置
            {
                t = a[i];
                a[i] = a[j];
                a[j] = t;
            }
        }
        //最终将基准数归位   归位后，基准点左边都小于此时的基准，右边都大于此时的基准
        a[left] = a[i];
        a[i] = temp;
        return i;
    }

}
