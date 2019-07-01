package com.itheima;

import java.text.SimpleDateFormat;
import java.util.Date;

public class demo1 {
    public static void main(String[] args)throws Exception {

        Date date = new Date();
        System.out.println(date);

        Date date2 = new Date(1000);
        System.out.println(date2);

        long time = date.getTime();
        System.out.println(time);

        long time2 = date2.getTime();
        System.out.println(time2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        String a = sdf.format(date);
        System.out.println(a);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str2 = "2019-3-20 16:19:50";
        Date date3 = sdf2.parse(str2);
        System.out.println(date3);

    }
}
