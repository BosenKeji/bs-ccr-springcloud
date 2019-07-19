package cn.bosenkeji.result.mock;

/**
 * @Author CAJR
 * @create 2019/7/19 16:47
 */
public class TradePlatformApiControllerResultMock {

    public String list(){
        return "{\n" +
                "  \"total\": 3,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"userId\": 1,\n" +
                "      \"tradePlatformId\": 1,\n" +
                "      \"sign\": \"1\",\n" +
                "      \"accessKey\": \"1111111\",\n" +
                "      \"secretKey\": \"111111\",\n" +
                "      \"nickname\": \"123\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-16T22:22:38.000+0000\",\n" +
                "      \"updatedAt\": \"1970-01-01T00:00:01.000+0000\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 3,\n" +
                "      \"userId\": 0,\n" +
                "      \"tradePlatformId\": 2,\n" +
                "      \"sign\": null,\n" +
                "      \"accessKey\": \"\",\n" +
                "      \"secretKey\": \"\",\n" +
                "      \"nickname\": \"\",\n" +
                "      \"status\": 0,\n" +
                "      \"createdAt\": \"2019-07-19T06:41:36.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-19T06:41:36.000+0000\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 4,\n" +
                "      \"userId\": 3,\n" +
                "      \"tradePlatformId\": 3,\n" +
                "      \"sign\": null,\n" +
                "      \"accessKey\": \"123\",\n" +
                "      \"secretKey\": \"123\",\n" +
                "      \"nickname\": \"test\",\n" +
                "      \"status\": 0,\n" +
                "      \"createdAt\": \"2019-07-19T06:45:12.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-19T06:45:12.000+0000\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pageNum\": 1,\n" +
                "  \"pageSize\": 10,\n" +
                "  \"size\": 3,\n" +
                "  \"startRow\": 1,\n" +
                "  \"endRow\": 3,\n" +
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
                "  \"userId\": 1,\n" +
                "  \"tradePlatformId\": 1,\n" +
                "  \"sign\": \"1\",\n" +
                "  \"accessKey\": \"1111111\",\n" +
                "  \"secretKey\": \"111111\",\n" +
                "  \"nickname\": \"123\",\n" +
                "  \"status\": 1,\n" +
                "  \"createdAt\": \"2019-07-16T22:22:38.000+0000\",\n" +
                "  \"updatedAt\": \"1970-01-01T00:00:01.000+0000\"\n" +
                "}";
    }
}
