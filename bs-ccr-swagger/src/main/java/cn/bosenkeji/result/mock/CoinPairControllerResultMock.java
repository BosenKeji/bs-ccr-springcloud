package cn.bosenkeji.result.mock;

/**
 * @Author CAJR
 * @create 2019/7/19 16:38
 */
public class CoinPairControllerResultMock {
    public String list(){
        return "{\n" +
                "  \"total\": 3,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"1111\",\n" +
                "      \"isPopular\": 0,\n" +
                "      \"isOfficialSet\": 0,\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-16T22:20:15.000+0000\",\n" +
                "      \"updatedAt\": \"1970-01-01T00:00:01.000+0000\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"name\": \"222\",\n" +
                "      \"isPopular\": 0,\n" +
                "      \"isOfficialSet\": 0,\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-18T23:14:24.000+0000\",\n" +
                "      \"updatedAt\": \"1970-01-01T00:00:01.000+0000\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 3,\n" +
                "      \"name\": \"333\",\n" +
                "      \"isPopular\": 0,\n" +
                "      \"isOfficialSet\": 0,\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-18T23:14:28.000+0000\",\n" +
                "      \"updatedAt\": \"1970-01-01T00:00:01.000+0000\"\n" +
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
                "  \"name\": \"1111\",\n" +
                "  \"isPopular\": 0,\n" +
                "  \"isOfficialSet\": 0,\n" +
                "  \"status\": 1,\n" +
                "  \"createdAt\": \"2019-07-16T22:20:15.000+0000\",\n" +
                "  \"updatedAt\": \"1970-01-01T00:00:01.000+0000\"\n" +
                "}";
    }
}
