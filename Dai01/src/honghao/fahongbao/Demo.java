package honghao.fahongbao;

public class Demo {
    public static void main(String[] args) {

        //创建红包界面的对象
        MyRedFrame mrf = new MyRedFrame("抢红包啦");
        //设置群主发红包人的名字，红包金额会转换成分，扩大10倍
        mrf.setOwnerName("英豪大富豪");
        //平均分配
       // mrf.setOpenWay(new NormlMode());
        //随机分配
        mrf.setOpenWay(new RandomMode());

    }
}
