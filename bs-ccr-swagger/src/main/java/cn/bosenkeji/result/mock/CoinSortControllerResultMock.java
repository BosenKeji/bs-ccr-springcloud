package cn.bosenkeji.result.mock;

/**
 * @Author CAJR
 * @create 2019/7/19 16:45
 */
public class CoinSortControllerResultMock {

    public String list(){
        return "{\n" +
                "  \"total\": 1,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"tradePlatformId\": 1,\n" +
                "      \"coinId\": 1,\n" +
                "      \"type\": 1,\n" +
                "      \"sortNum\": 1,\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-16T22:21:52.000+0000\",\n" +
                "      \"updatedAt\": \"1970-01-01T00:00:01.000+0000\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pageNum\": 1,\n" +
                "  \"pageSize\": 10,\n" +
                "  \"size\": 1,\n" +
                "  \"startRow\": 1,\n" +
                "  \"endRow\": 1,\n" +
                "  \"pages\": 1,\n" +
                "  \"prePage\": 0,\n" +
                "  \"nextPage\": 0,\n" +
                "  \"isFirstPage\": true,\n" +
                "  \"isLastPage\": true,\n" +
                "  \"hasPreviousPage\": false,\n" +
                "  \"hasNextPage\": false,\n" +
                "  \"navigatePages\": 8,\n" +
                "  \"navigatepageNums\": [\n" +
                "    1\n" +
                "  ],\n" +
                "  \"navigateFirstPage\": 1,\n" +
                "  \"navigateLastPage\": 1\n" +
                "}";
    }

    public String get(){
        return "{\n" +
                "  \"id\": 1,\n" +
                "  \"tradePlatformId\": 1,\n" +
                "  \"coinId\": 1,\n" +
                "  \"type\": 1,\n" +
                "  \"sortNum\": 1,\n" +
                "  \"status\": 1,\n" +
                "  \"createdAt\": \"2019-07-16T22:21:52.000+0000\",\n" +
                "  \"updatedAt\": \"1970-01-01T00:00:01.000+0000\"\n" +
                "}";
    }
}
