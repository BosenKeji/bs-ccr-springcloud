package cn.bosenkeji.result.mock;

public class StrategyControllerResultMock {

    public String addStrategyBySelective() {
        return "true";
    }

    public String addStrategyAttributeBySelective() {
        return "true";
    }

    public String get() {
        return "{\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"保守\",\n" +
                "    \"status\": 1,\n" +
                "    \"lever\": 3,\n" +
                "    \"rate\": 2,\n" +
                "    \"buildReference\": 4\n" +
                "}";
    }

    public String listByPage() {
        return "{\n" +
                "    \"total\": 8,\n" +
                "    \"list\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"保守-\",\n" +
                "            \"status\": 1,\n" +
                "            \"createdAt\": \"2019-07-15T22:03:59.000+0000\",\n" +
                "            \"updatedAt\": \"1970-01-01T00:00:01.000+0000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"name\": \"保守\",\n" +
                "            \"status\": 1,\n" +
                "            \"createdAt\": \"2019-07-15T22:04:08.000+0000\",\n" +
                "            \"updatedAt\": \"1970-01-01T00:00:01.000+0000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"保守+\",\n" +
                "            \"status\": 1,\n" +
                "            \"createdAt\": \"2019-07-15T22:04:22.000+0000\",\n" +
                "            \"updatedAt\": \"1970-01-01T00:00:01.000+0000\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"pageNum\": 1,\n" +
                "    \"pageSize\": 3,\n" +
                "    \"size\": 3,\n" +
                "    \"startRow\": 1,\n" +
                "    \"endRow\": 3,\n" +
                "    \"pages\": 3,\n" +
                "    \"prePage\": 0,\n" +
                "    \"nextPage\": 2,\n" +
                "    \"isFirstPage\": true,\n" +
                "    \"isLastPage\": false,\n" +
                "    \"hasPreviousPage\": false,\n" +
                "    \"hasNextPage\": true,\n" +
                "    \"navigatePages\": 8,\n" +
                "    \"navigatepageNums\": [\n" +
                "        1,\n" +
                "        2,\n" +
                "        3\n" +
                "    ],\n" +
                "    \"navigateFirstPage\": 1,\n" +
                "    \"navigateLastPage\": 3\n" +
                "}";
    }
}
