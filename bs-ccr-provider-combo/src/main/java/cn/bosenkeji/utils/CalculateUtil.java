package cn.bosenkeji.utils;

import java.util.ArrayList;
import java.util.List;

public class CalculateUtil {

    public static final int _5000=5000;

    //除以5000 取整
    public static int dividedBy5000(int number) {

        return number/_5000;
    }

    //除以5000取余
    public static int dividedBy5000ForRemainder(int number) {

        return number%_5000;
    }


    public static void main(String[] args) {

        List list=new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list.toString());
        System.out.println("5000 取整"+dividedBy5000(10000));
        System.out.println("5000 取余"+dividedBy5000ForRemainder(10000));
    }


}
