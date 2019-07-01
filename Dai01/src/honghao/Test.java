package honghao;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {

        //创建一个群主设置名字和余额
        QunZhu qz = new QunZhu("群主", 200);

        //创建群员和余额
        QunYuan qy1 = new QunYuan("赵一", 0);
        QunYuan qy2 = new QunYuan("钱二", 0);
        QunYuan qy3 = new QunYuan("孙三", 0);

        //群主开始发红包
        ArrayList<Integer> list2 = qz.fahongbao(160, 3);

        //判断余额是否能发红包
        if (list2 == null) {
            System.out.println("余额不足");
            return;
        }

        //群员抢红包
        qy1.qianghongbao(list2);
        qy2.qianghongbao(list2);
        qy3.qianghongbao(list2);

        //展示信息
        qy1.show();
        qy2.show();
        qy3.show();
        qz.show();

    }
}
