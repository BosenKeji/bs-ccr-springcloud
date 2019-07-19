package cn.bosenkeji.result.mock;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.result.mock
 * @Version V1.0
 * @create 2019-07-19 16:38
 */
public class ProductControllerResultMock {
    public String list(){
        //此处应写成实体类相关的数据
        return "{\n" +
                "  \"total\": 2,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"string\",\n" +
                "      \"versionName\": \"string\",\n" +
                "      \"logo\": \"string\",\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 2,\n" +
                "      \"createdAt\": \"2019-07-16T18:07:02.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-16T17:48:45.000+0000\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"name\": \"name2\",\n" +
                "      \"versionName\": \"banbenming\",\n" +
                "      \"logo\": \"logo2\",\n" +
                "      \"remark\": \"beizhu\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-19T08:48:18.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-19T08:48:18.000+0000\"\n" +
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

    public String get(){
        //此处应写成实体类相关的数据
        return "{\n" +
                "  \"id\": 2,\n" +
                "  \"name\": \"name2\",\n" +
                "  \"versionName\": \"banbenming\",\n" +
                "  \"logo\": \"logo2\",\n" +
                "  \"remark\": \"beizhu\",\n" +
                "  \"status\": 1,\n" +
                "  \"createdAt\": \"2019-07-19T08:48:18.000+0000\",\n" +
                "  \"updatedAt\": \"2019-07-19T08:48:18.000+0000\"\n" +
                "}";
    }
}
