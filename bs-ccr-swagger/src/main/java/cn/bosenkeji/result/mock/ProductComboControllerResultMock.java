package cn.bosenkeji.result.mock;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.result.mock
 * @Version V1.0
 * @create 2019-07-19 16:50
 */
public class ProductComboControllerResultMock {
    public String list() {
        return "{\n" +
                "  \"total\": 6,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"productId\": 1,\n" +
                "      \"name\": \"string\",\n" +
                "      \"time\": 30,\n" +
                "      \"price\": 10,\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 0,\n" +
                "      \"createdAt\": \"2019-07-16T18:26:37.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-16T18:26:37.000+0000\",\n" +
                "      \"product\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"productId\": 0,\n" +
                "      \"name\": \"string2\",\n" +
                "      \"time\": 10,\n" +
                "      \"price\": 0,\n" +
                "      \"remark\": \"string2\",\n" +
                "      \"status\": 5,\n" +
                "      \"createdAt\": \"2019-07-17T23:17:49.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-18T07:12:16.000+0000\",\n" +
                "      \"product\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 3,\n" +
                "      \"productId\": 2,\n" +
                "      \"name\": \"string\",\n" +
                "      \"time\": 30,\n" +
                "      \"price\": 10,\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 0,\n" +
                "      \"createdAt\": \"2019-07-16T22:26:57.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-16T18:30:41.000+0000\",\n" +
                "      \"product\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 4,\n" +
                "      \"productId\": 1,\n" +
                "      \"name\": \"1\",\n" +
                "      \"time\": 0,\n" +
                "      \"price\": 0,\n" +
                "      \"remark\": \"\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-17T18:58:26.000+0000\",\n" +
                "      \"updatedAt\": \"1979-12-31T16:00:01.000+0000\",\n" +
                "      \"product\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 6,\n" +
                "      \"productId\": 0,\n" +
                "      \"name\": \"string\",\n" +
                "      \"time\": 0,\n" +
                "      \"price\": 0,\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 0,\n" +
                "      \"createdAt\": \"2019-07-18T09:16:25.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-18T09:16:25.000+0000\",\n" +
                "      \"product\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 7,\n" +
                "      \"productId\": 2,\n" +
                "      \"name\": \"更新\",\n" +
                "      \"time\": 10,\n" +
                "      \"price\": 0,\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 2,\n" +
                "      \"createdAt\": \"2019-07-18T02:51:41.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-18T10:46:08.000+0000\",\n" +
                "      \"product\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pageNum\": 1,\n" +
                "  \"pageSize\": 15,\n" +
                "  \"size\": 6,\n" +
                "  \"startRow\": 1,\n" +
                "  \"endRow\": 6,\n" +
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

    public String get() {
        return "{\n" +
                "  \"id\": 1,\n" +
                "  \"productId\": 1,\n" +
                "  \"name\": \"string\",\n" +
                "  \"time\": 30,\n" +
                "  \"price\": 10,\n" +
                "  \"remark\": \"string\",\n" +
                "  \"status\": 0,\n" +
                "  \"createdAt\": \"2019-07-16T18:26:37.000+0000\",\n" +
                "  \"updatedAt\": \"2019-07-16T18:26:37.000+0000\",\n" +
                "  \"product\": null\n" +
                "}";
    }

    //根据产品id查询套餐列表
    public String listByProductId() {
        return "{\n" +
                "  \"total\": 2,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"productId\": 1,\n" +
                "      \"name\": \"string\",\n" +
                "      \"time\": 30,\n" +
                "      \"price\": 10,\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 0,\n" +
                "      \"createdAt\": \"2019-07-16T18:26:37.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-16T18:26:37.000+0000\",\n" +
                "      \"product\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 4,\n" +
                "      \"productId\": 1,\n" +
                "      \"name\": \"1\",\n" +
                "      \"time\": 0,\n" +
                "      \"price\": 0,\n" +
                "      \"remark\": \"\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-17T18:58:26.000+0000\",\n" +
                "      \"updatedAt\": \"1979-12-31T16:00:01.000+0000\",\n" +
                "      \"product\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pageNum\": 1,\n" +
                "  \"pageSize\": 15,\n" +
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

    //根据产品id和套餐转态status获取产品套餐列表
    public String listByProductIdAndStatus() {
        return "{\n" +
                "  \"total\": 1,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 4,\n" +
                "      \"productId\": 1,\n" +
                "      \"name\": \"1\",\n" +
                "      \"time\": 0,\n" +
                "      \"price\": 0,\n" +
                "      \"remark\": \"\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-17T18:58:26.000+0000\",\n" +
                "      \"updatedAt\": \"1979-12-31T16:00:01.000+0000\",\n" +
                "      \"product\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pageNum\": 1,\n" +
                "  \"pageSize\": 15,\n" +
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
}
