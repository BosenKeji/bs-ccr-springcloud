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
                "  \"total\": 3,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"CCR智能交易机器人\",\n" +
                "      \"versionName\": \"CCR智能交易机器人\",\n" +
                "      \"logo\": \"sgeoinlogh\",\n" +
                "      \"remark\": \"仅支持AVA和DAVA交易平台\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-28T18:53:49.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-26T12:47:58.000+0000\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"name\": \"FA（共享版）\",\n" +
                "      \"versionName\": \"FA（共享版）\",\n" +
                "      \"logo\": \"/img/fagognxianglol.img\",\n" +
                "      \"remark\": \"不限交易平台\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-28T18:53:59.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-25T01:42:03.000+0000\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 3,\n" +
                "      \"name\": \"FA（独立版）\",\n" +
                "      \"versionName\": \"FA（独立版）\",\n" +
                "      \"logo\": \"/img/fadulilogo.img\",\n" +
                "      \"remark\": \"适用于数字货币交易\",\n" +
                "      \"status\": 2,\n" +
                "      \"createdAt\": \"2019-07-28T18:54:33.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-23T02:32:35.000+0000\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pageNum\": 1,\n" +
                "  \"pageSize\": 15,\n" +
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
        //此处应写成实体类相关的数据
        return "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"CCR智能交易机器人\",\n" +
                "  \"versionName\": \"CCR智能交易机器人\",\n" +
                "  \"logo\": \"sgeoinlogh\",\n" +
                "  \"remark\": \"仅支持AVA和DAVA交易平台\",\n" +
                "  \"status\": 1,\n" +
                "  \"createdAt\": \"2019-07-28T18:53:49.000+0000\",\n" +
                "  \"updatedAt\": \"2019-07-26T12:47:58.000+0000\"\n" +
                "}";
    }
}
