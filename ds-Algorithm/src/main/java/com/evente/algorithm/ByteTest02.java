package com.evente.algorithm;

/**
 *
 */
public class ByteTest02 {

    // TODO :
    public static void main(String[] args) {
        int[] a = {1, 1, 1,  1};
        int[] b = new int[14];

        System.arraycopy(a, 0, b, 10, 4);

        for (int i = 0; i < b.length; i++) {
            b[i] += 1;
        }

        for (int i = 0; i < a.length; i++) {
            System.out.println("a[" + i + "] = " + a[i]);
        }
        System.out.println("---------------------------------");
        for (int i = 0; i < b.length; i++) {
            System.out.println("b[" + i + "] = " + b[i]);
        }
        System.out.println("---------------------------------");
        double[] c = new double[4];
        for (int i = 0; i < c.length; i++) {
            System.out.println("c[" + i + "] = " + c[i]);
        }



    }
}
