package com.itheima;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class demo2 {
    public static void main(String[] args)throws Exception {

//请使用日期时间相关的API，计算出一个人已经出生了多少天


        Scanner san = new Scanner(System.in);
        System.out.println("请输入你的生日，格式为：yyyy-MM-dd");
        String str = san.next();//将你输入的生日转化为字符串格式

        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd");//创建日期模式
        Date parse = sim.parse(str);//将str转化为日期对象

        Date date = new Date();//获取当前时间

        long time = date.getTime();//将当前时间转化为毫秒
        long time1 = parse.getTime();//将输入的日期转化为毫秒
        long time3 = time - time1;
        if(time3 < 0){
            System.out.println("还没出生呢");
        }else{
            System.out.println(time3/1000/60/60/24);
        }

    }
}
