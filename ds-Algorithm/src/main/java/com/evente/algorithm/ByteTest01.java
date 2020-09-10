package com.evente.algorithm;

public class ByteTest01 {
    public static void main(String[] args) {
        int[] a = {1, 1, 1,  1};
        int[] b = a;

        for (int i = 0; i < b.length; i++) {
            b[i] += 1;
        }

        for (int i = 0; i < a.length; i++) {
            System.out.println("a[" + i + "] = " + a[i]);
        }
    }
}
