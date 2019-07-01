package honghao;

public class User {

    String name;
    int leftmoney;

    public User() {

    }

    public User(String name, int leftmoney) {
        this.name = name;
        this.leftmoney = leftmoney;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLeftmoney() {
        return leftmoney;
    }

    public void setLeftmoney(int leftmoney) {
        this.leftmoney = leftmoney;
    }

    public void show() {
        System.out.println("姓名：" + getName() + ",余额：" + getLeftmoney() + "元");
    }
}
