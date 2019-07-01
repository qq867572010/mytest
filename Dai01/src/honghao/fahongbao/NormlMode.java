package honghao.fahongbao;

import honghao.red.OpenMode;

import java.util.ArrayList;

public class NormlMode implements OpenMode {
    @Override
    public ArrayList<Integer> divide(int totalMoney, int totalCount) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        int a = totalMoney / totalCount;
        int b = totalMoney % totalCount;
        for (int i = 0; i < totalCount - 1; i++) {
            list.add(a);
        }
        list.add(a + b);
        return list;
    }
}
