package honghao;

import java.util.ArrayList;

public class QunZhu extends User {

    public QunZhu() {
    }

    public QunZhu(String name, int leftmoney) {
        super(name, leftmoney);
    }

    public ArrayList<Integer> fahongbao(int money, int n) {
        //获取余额
        int leftmoney = getLeftmoney();
        //创建一个收集分配红包的集合
        ArrayList<Integer> list = new ArrayList<Integer>();
        //判断余额是否够发红包
        if (money > leftmoney) {
            //不够则null
            return null;
        } else {
            //够则发红包
            //平均分红包，最后一位得平均数和红包余额
            int a = money / n;
            int b = money % n;
            for (int i = 0; i < n - 1; i++) {
                //将余额放入集合中
                list.add(a);
            }
            //最后一位获得平均值和红包余额
            list.add(a + b);
        }
        //修改群主的余额
        setLeftmoney(leftmoney - money);
        //返回集合
        return list;
    }
}
