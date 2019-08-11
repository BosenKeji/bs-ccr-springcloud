package cn.bosenkeji.result.demo;

import cn.bosenkeji.vo.result.demo.BaseResultDemo;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.result.demo
 * @Version V1.0
 * @create 2019-07-19 10:37
 */
public class ProductResultDemo extends BaseResultDemo {


        public static final String PRODUCT_LIST_PAGE_RESTFUL= "{\n" +
                "  \"total\": 2,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"name\": \"btc\",\n" +
                "      \"versionName\": \"btc\",\n" +
                "      \"logo\": \"btc\",\n" +
                "      \"remark\": \"btc\",\n" +
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


        public static final String PORDUCT_DETAIL_RESTFUL= "{\n" +
                "  \"id\": 1,\n" +
                "  \"name\": \"btc\",\n" +
                "  \"versionName\": \"btc\",\n" +
                "  \"logo\": \"btc\",\n" +
                "  \"remark\": \"btc\",\n" +
                "  \"status\": 0,\n" +
                "  \"createdAt\": \"2019-07-17T19:13:37.000+0000\",\n" +
                "  \"updatedAt\": \"2019-07-17T19:13:37.000+0000\"\n" +
                "}";


}
