package honghao;

import java.util.ArrayList;
import java.util.Random;

public class QunYuan extends User {

    public QunYuan() {
    }

    public QunYuan(String name, int leftmoney) {
        super(name, leftmoney);
    }

    public void qianghongbao(ArrayList<Integer> list) {
        //生成一个随机索引
        int index = new Random().nextInt(list.size());
        //随机分配红包
        Integer redmoney = list.get(index);
        //取到红包后在红包里面将其移除
        list.remove(index);
        //将取到的红包存入余额中
        int leftmoney = getLeftmoney();//取出的是这个群员的余额
        setLeftmoney(leftmoney + redmoney);//将群员原本的余额和抢到的红包存入他自己的余额中
    }
}
