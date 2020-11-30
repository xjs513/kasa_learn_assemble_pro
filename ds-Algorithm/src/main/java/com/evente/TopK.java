package com.evente;

import java.util.TreeSet;

/**
 * @author : Kasa
 * @date : 2020/11/23 14:35
 * @descripthon :
 */
public class TopK {

    // 最小的K个数

    static void getTopK(int[] arrys, int k){
        TreeSet<Integer> set = new TreeSet<>();
        for (int ele : arrys) {
            if (set.size()<k)
                set.add(ele);
            else {
                if (set.last() > ele){
                    set.remove(set.last());
                    set.add(ele);
                }
            }
        }
        System.out.println(set);
    }


    public static void main(String[] args) {
//        TreeSet<Integer> set = new TreeSet<>();
//        set.add(3);
//        set.add(1);
//        set.add(2);
//        System.out.println(set);
        getTopK(new int[]{20, 30, 40, 2, 4, 8}, 3);

    }
}
