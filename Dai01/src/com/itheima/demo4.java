package com.itheima;

import java.util.Arrays;

public class demo4 {
    public static void main(String[] args) {

        long time = System.currentTimeMillis();
        for (int a = 0; a < 100; a++) {
            System.out.println(a);
        }
        long time2 = System.currentTimeMillis();
        System.out.println("总共耗时：" +(time2 - time));

        int[] arr = {1,2,3,4,5};
        int[] arr2 = {6,7,8,9,10};
        System.arraycopy(arr,0,arr2,0,3);
        System.out.println(Arrays.toString(arr2));
    }
}
