package cn.bosenkeji.utils;

/**
 * @author CAJR
 * @date 2019/12/26 7:51 下午
 */
public class MqMessageUtil {
    public static String getString(Object o) {
        return o == null ? "" : o.toString();
    }

    public static Integer getInteger(Object o) {
        Integer temp = 0;
        if (o != null) {
            temp = Integer.valueOf(o.toString());
        }
        return temp;
    }

    public static Double getDouble(Object o) {
        Double temp;
        temp = o == null ? Double.valueOf("0.0") : Double.valueOf(o.toString());
        return temp;
    }
}
