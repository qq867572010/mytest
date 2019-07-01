package honghao.fahongbao;

import honghao.red.OpenMode;

import java.util.ArrayList;
import java.util.Random;

public class RandomMode implements OpenMode {
    @Override
    public ArrayList<Integer> divide(int totalMoney, int totalCount) {
        //建立一个集合用来存储红包
        ArrayList<Integer> list = new ArrayList<Integer>();
        //设置随机生成器
        Random ran = new Random();
        //设置剩余金额
        int leftmoney = totalMoney;
        //设置红包个数
        int leftcount = totalCount;
        for (int a = 0; a < totalCount - 1; a++) {
            int money = ran.nextInt(leftmoney / leftcount * 2) + 1;
            //修改剩余金额
            leftmoney = leftmoney - money;
            //修改红包个数
            leftcount--;
            //将红包存入集合中
            list.add(money);
        }
        list.add(leftmoney);
        return list;
    }
}
