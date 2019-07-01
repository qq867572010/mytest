package com.itheima;

import java.sql.SQLOutput;
import java.util.Calendar;
import java.util.Date;

public class demo3 {
    public static void main(String[] args) {

        Calendar ca = Calendar.getInstance();
        System.out.println(ca);

//        int year = ca.get(Calendar.YEAR);
//        int month = ca.get(Calendar.MONTH)+1;
//        int dayofMonth = ca.get(Calendar.DAY_OF_MONTH);
//        System.out.println(year+"年"+month+"月"+dayofMonth+"日");

//        ca.set(Calendar.YEAR,2000);
//        System.out.println(ca.get(Calendar.YEAR));

//        ca.add(Calendar.YEAR,3);
//        ca.add(Calendar.MONTH,3);
//        System.out.println(ca.get(Calendar.YEAR));
//        System.out.println(ca.get(Calendar.MONTH));


        Date time = ca.getTime();
        System.out.println(time);

    }
}
