package cn.bosenkeji.result.mock;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

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
                "      \"name\": \"体验\",\n" +
                "      \"time\": 14,\n" +
                "      \"price\": 0,\n" +
                "      \"remark\": \"注意已有产品时不能部署\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-28T18:57:05.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-25T12:02:59.000+0000\",\n" +
                "      \"product\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"productId\": 1,\n" +
                "      \"name\": \"半年\",\n" +
                "      \"time\": 180,\n" +
                "      \"price\": 540,\n" +
                "      \"remark\": \"注意已有产品时不能部署\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-27T03:41:38.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-23T06:33:51.000+0000\",\n" +
                "      \"product\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 3,\n" +
                "      \"productId\": 2,\n" +
                "      \"name\": \"一年\",\n" +
                "      \"time\": 360,\n" +
                "      \"price\": 888,\n" +
                "      \"remark\": \"注意已有产品时不能部署\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-27T03:42:12.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-16T18:30:41.000+0000\",\n" +
                "      \"product\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 4,\n" +
                "      \"productId\": 1,\n" +
                "      \"name\": \"一年\",\n" +
                "      \"time\": 360,\n" +
                "      \"price\": 850,\n" +
                "      \"remark\": \"\",\n" +
                "      \"status\": 2,\n" +
                "      \"createdAt\": \"2019-07-27T03:42:41.000+0000\",\n" +
                "      \"updatedAt\": \"1979-12-31T16:00:01.000+0000\",\n" +
                "      \"product\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 5,\n" +
                "      \"productId\": 1,\n" +
                "      \"name\": \"试用\",\n" +
                "      \"time\": 7,\n" +
                "      \"price\": 0,\n" +
                "      \"remark\": \"\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-28T18:56:40.000+0000\",\n" +
                "      \"updatedAt\": \"1979-12-31T16:00:01.000+0000\",\n" +
                "      \"product\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 6,\n" +
                "      \"productId\": 3,\n" +
                "      \"name\": \"半年\",\n" +
                "      \"time\": 180,\n" +
                "      \"price\": 480,\n" +
                "      \"remark\": \"注意已有产品时不能部署\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-28T18:56:06.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-18T09:16:25.000+0000\",\n" +
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
                "  \"name\": \"体验\",\n" +
                "  \"time\": 14,\n" +
                "  \"price\": 0,\n" +
                "  \"remark\": \"注意已有产品时不能部署\",\n" +
                "  \"status\": 1,\n" +
                "  \"createdAt\": \"2019-07-28T18:57:05.000+0000\",\n" +
                "  \"updatedAt\": \"2019-07-25T12:02:59.000+0000\",\n" +
                "  \"product\": null\n" +
                "}";
    }

    //根据产品id查询套餐列表
    public String listByProductId() {
        return "{\n" +
                "  \"total\": 4,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"productId\": 1,\n" +
                "      \"name\": \"体验\",\n" +
                "      \"time\": 14,\n" +
                "      \"price\": 0,\n" +
                "      \"remark\": \"注意已有产品时不能部署\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-28T18:57:05.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-25T12:02:59.000+0000\",\n" +
                "      \"product\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"productId\": 1,\n" +
                "      \"name\": \"半年\",\n" +
                "      \"time\": 180,\n" +
                "      \"price\": 540,\n" +
                "      \"remark\": \"注意已有产品时不能部署\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-27T03:41:38.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-23T06:33:51.000+0000\",\n" +
                "      \"product\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 4,\n" +
                "      \"productId\": 1,\n" +
                "      \"name\": \"一年\",\n" +
                "      \"time\": 360,\n" +
                "      \"price\": 850,\n" +
                "      \"remark\": \"\",\n" +
                "      \"status\": 2,\n" +
                "      \"createdAt\": \"2019-07-27T03:42:41.000+0000\",\n" +
                "      \"updatedAt\": \"1979-12-31T16:00:01.000+0000\",\n" +
                "      \"product\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 5,\n" +
                "      \"productId\": 1,\n" +
                "      \"name\": \"试用\",\n" +
                "      \"time\": 7,\n" +
                "      \"price\": 0,\n" +
                "      \"remark\": \"\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-28T18:56:40.000+0000\",\n" +
                "      \"updatedAt\": \"1979-12-31T16:00:01.000+0000\",\n" +
                "      \"product\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pageNum\": 1,\n" +
                "  \"pageSize\": 15,\n" +
                "  \"size\": 4,\n" +
                "  \"startRow\": 1,\n" +
                "  \"endRow\": 4,\n" +
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
                "  \"total\": 3,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"productId\": 1,\n" +
                "      \"name\": \"体验\",\n" +
                "      \"time\": 14,\n" +
                "      \"price\": 0,\n" +
                "      \"remark\": \"注意已有产品时不能部署\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-28T18:57:05.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-25T12:02:59.000+0000\",\n" +
                "      \"product\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"productId\": 1,\n" +
                "      \"name\": \"半年\",\n" +
                "      \"time\": 180,\n" +
                "      \"price\": 540,\n" +
                "      \"remark\": \"注意已有产品时不能部署\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-27T03:41:38.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-23T06:33:51.000+0000\",\n" +
                "      \"product\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 5,\n" +
                "      \"productId\": 1,\n" +
                "      \"name\": \"试用\",\n" +
                "      \"time\": 7,\n" +
                "      \"price\": 0,\n" +
                "      \"remark\": \"\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-28T18:56:40.000+0000\",\n" +
                "      \"updatedAt\": \"1979-12-31T16:00:01.000+0000\",\n" +
                "      \"product\": null\n" +
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

}
