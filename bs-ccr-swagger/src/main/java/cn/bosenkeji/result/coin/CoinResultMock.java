package cn.bosenkeji.result.coin;

/**
 * @ClassName CoinResultMock
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
public class CoinResultMock {
    public String list(){
        //此处应写成实体类相关的数据
        return "{\n" +
                "  \"total\": 2,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"btc\",\n" +
                "      \"status\": 0,\n" +
                "      \"createdAt\": \"2019-07-17T19:13:37.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-17T19:13:37.000+0000\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"name\": \"usdt\",\n" +
                "      \"status\": 0,\n" +
                "      \"createdAt\": \"2019-07-17T19:15:53.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-17T19:15:53.000+0000\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pageNum\": 0,\n" +
                "  \"pageSize\": 10,\n" +
                "  \"size\": 2,\n" +
                "  \"startRow\": 1,\n" +
                "  \"endRow\": 2,\n" +
                "  \"pages\": 1,\n" +
                "  \"prePage\": 0,\n" +
                "  \"nextPage\": 1,\n" +
                "  \"isFirstPage\": false,\n" +
                "  \"isLastPage\": false,\n" +
                "  \"hasPreviousPage\": false,\n" +
                "  \"hasNextPage\": true,\n" +
                "  \"navigatePages\": 8,\n" +
                "  \"navigatepageNums\": [\n" +
                "    1\n" +
                "  ],\n" +
                "  \"navigateFirstPage\": 1,\n" +
                "  \"navigateLastPage\": 1\n" +
                "}";
    }

    public String get(){
        //此处应写成实体类相关的数据
        return "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"btc\",\n" +
                "  \"status\": 0,\n" +
                "  \"createdAt\": \"2019-07-17T19:13:37.000+0000\",\n" +
                "  \"updatedAt\": \"2019-07-17T19:13:37.000+0000\"\n" +
                "}";
    }


}
