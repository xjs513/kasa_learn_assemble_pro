package com.evente.algorithm.sort;

import java.util.Arrays;

/**
 * @author : Kasa
 * @date : 2020/9/22 16:24
 * @descripthon :
 *
 * https://blog.csdn.net/qq_20009015/article/details/89289504
 *
 * 1.快排的思路，一个无序数组，选中一个值作为中值，从左右两个端点开始，定义两个左右指针
 * 2.从右向左找比这个值要大的数，找到就停止，否则指针继续向左移动一个位置，直到找到或者已经比左指针位置小。
 * 3.从左向右找比这个值要小的数，找到就停止，否则指针继续向右移动一个位置，直到找到或者已经比右指针位置大。
 *
 * 4.然后交换这两个数字的位置。
 *
 * 重复2 3 4 步骤，直到左指针位置已经不再小于右指针位置
 * 5.然后将中值和现在的左指针或者右指针位置的数 交换位置
 *
 * 此时这个数组，就已经以中值为界限，中值左边的均小于中值，中值右边的均大于中值
 *
 * 6.最后递归重复以上的所有步骤 即可将一个无序数组完整的排序。
 *
 * 代码的思路：
 * 定义一个可以递归执行的fastSort方法，参数为left（左下标） right（右下标） array（数组）
 *
 * 核心方法为getPartition(left, right, arr); 这个方法里面 执行了12345步骤，有排序和返回中值下标的作用
 */
public class QuickSortTest2 {


    static void fastSort(Integer left, Integer right, int[] arr) {
        if (left >= right) {
            return;
        }
        //进行排序并获取中值
        Integer partition = getPartition(left, right, arr);
        //重复以上
        fastSort(left, partition - 1, arr);
        fastSort(partition + 1, right, arr);
    }

    public static void main(String[] args) {

        int[] ints = {2, 3, 1, 0, 2, 5, 3};
        System.out.println(Arrays.toString(ints));
        fastSort(0,ints.length-1,ints);
        System.out.println(Arrays.toString(ints));
    }

    /**
     * 进行排序和获取中值
     *
     * @param left
     * @param right
     * @param ints
     * @return
     */
    static   Integer getPartition(Integer left, Integer right, int[] ints) {
        //默认中值选取为第一个数组中left的值
        Integer value = ints[left];
        Integer partition=left;

        while (left < right) {
            // 找右边第一个比中值小的
            // TODO : 这里千万注意！ 一定要右边的指针先走，就是先从右往左找比中值小的数。
            while (ints[right] >= value && left < right) {
                right--;
            }
            //找左边第一个比中值大的
            while (ints[left] <= value && left < right) {
                left++;
            }
            //交换这两个位置的值
            if (ints[left]>ints[right]){
                Integer temp = ints[left];
                ints[left] = ints[right];
                ints[right] = temp;
                System.out.println("left = " + left);
                System.out.println("right = " + right);
            }
            System.out.println(Arrays.toString(ints));
        }

        //将中值放到中间
        System.out.println("将中值" + value + "放到中间");
        System.out.println("partition = " + partition);
        System.out.println("left = " + left);
        ints[partition]=ints[left];
        ints[left]=value;
        System.out.println(Arrays.toString(ints));
        System.out.println("return left = " + left);
        return left;
    }
}
