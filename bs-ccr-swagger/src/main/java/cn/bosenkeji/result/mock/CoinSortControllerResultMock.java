package cn.bosenkeji.result.mock;

/**
 * @Author CAJR
 * @create 2019/7/19 16:45
 */
public class CoinSortControllerResultMock {

    public String list(){
        return "{\n" +
                "  \"total\": 2,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"tradePlatformId\": 1,\n" +
                "      \"coinId\": 1,\n" +
                "      \"type\": 1,\n" +
                "      \"sortNum\": 1,\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-23T09:54:20.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-23T09:54:20.000+0000\",\n" +
                "      \"coin\": {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"BTC\",\n" +
                "        \"status\": 1,\n" +
                "        \"createdAt\": \"2019-07-23T05:03:27.000+0000\",\n" +
                "        \"updatedAt\": \"2019-07-23T05:03:27.000+0000\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"tradePlatformId\": 1,\n" +
                "      \"coinId\": 2,\n" +
                "      \"type\": 1,\n" +
                "      \"sortNum\": 1,\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-24T20:48:31.000+0000\",\n" +
                "      \"updatedAt\": \"1970-01-01T00:00:01.000+0000\",\n" +
                "      \"coin\": {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"HT\",\n" +
                "        \"status\": 1,\n" +
                "        \"createdAt\": \"2019-07-23T05:03:29.000+0000\",\n" +
                "        \"updatedAt\": \"2019-07-22T21:04:09.000+0000\"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pageNum\": 1,\n" +
                "  \"pageSize\": 10,\n" +
                "  \"size\": 2,\n" +
                "  \"startRow\": 1,\n" +
                "  \"endRow\": 2,\n" +
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
