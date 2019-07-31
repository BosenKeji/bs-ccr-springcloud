package cn.bosenkeji.result.mock;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.result.mock
 * @Version V1.0
 * @create 2019-07-31 19:48
 */
public class UserControllerResultMock {

    public String get() {
        return "{\n" +
                "  \"id\": 1,\n" +
                "  \"username\": \"zhangsange\",\n" +
                "  \"tel\": \"12345678\",\n" +
                "  \"password\": \"12545\",\n" +
                "  \"status\": 1,\n" +
                "  \"createdAt\": \"2019-07-31T03:18:53.000+0000\",\n" +
                "  \"updatedAt\": \"2019-07-31T11:12:57.000+0000\"\n" +
                "}";
    }

    public String getByUsername() {
        return "{\n" +
                "  \"id\": 1,\n" +
                "  \"username\": \"zhangsan\",\n" +
                "  \"tel\": \"12345678\",\n" +
                "  \"password\": \"12545\",\n" +
                "  \"status\": 1,\n" +
                "  \"createdAt\": \"2019-07-31T03:59:13.000+0000\",\n" +
                "  \"updatedAt\": \"2019-07-31T11:12:57.000+0000\"\n" +
                "}";
    }
}
