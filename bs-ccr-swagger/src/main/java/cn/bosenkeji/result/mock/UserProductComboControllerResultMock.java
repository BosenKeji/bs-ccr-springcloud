package cn.bosenkeji.result.mock;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.result.mock
 * @Version V1.0
 * @create 2019-07-19 16:50
 */
public class UserProductComboControllerResultMock {
    public String list() {

        return "{\n" +
                "  \"total\": 6,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"userId\": 1,\n" +
                "      \"orderNumber\": \"string1\",\n" +
                "      \"productComboId\": 1,\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-17T03:28:48.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-16T18:44:50.000+0000\",\n" +
                "      \"remainTime\": 0,\n" +
                "      \"productCombo\": null,\n" +
                "      \"user\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"userId\": 1,\n" +
                "      \"orderNumber\": \"string2\",\n" +
                "      \"productComboId\": 1,\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 2,\n" +
                "      \"createdAt\": \"2019-07-17T03:28:50.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-16T18:45:47.000+0000\",\n" +
                "      \"remainTime\": 0,\n" +
                "      \"productCombo\": null,\n" +
                "      \"user\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 3,\n" +
                "      \"userId\": 2,\n" +
                "      \"orderNumber\": \"thi is 2\",\n" +
                "      \"productComboId\": 2,\n" +
                "      \"remark\": \"15\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-17T19:13:26.000+0000\",\n" +
                "      \"updatedAt\": \"1979-12-31T16:00:01.000+0000\",\n" +
                "      \"remainTime\": 0,\n" +
                "      \"productCombo\": null,\n" +
                "      \"user\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 4,\n" +
                "      \"userId\": 2,\n" +
                "      \"orderNumber\": \"string\",\n" +
                "      \"productComboId\": 1,\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 0,\n" +
                "      \"createdAt\": \"2019-07-18T12:01:24.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-18T12:01:24.000+0000\",\n" +
                "      \"remainTime\": 0,\n" +
                "      \"productCombo\": null,\n" +
                "      \"user\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 5,\n" +
                "      \"userId\": 1,\n" +
                "      \"orderNumber\": \"string\",\n" +
                "      \"productComboId\": 1,\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 0,\n" +
                "      \"createdAt\": \"2019-07-18T12:19:57.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-18T12:19:57.000+0000\",\n" +
                "      \"remainTime\": 0,\n" +
                "      \"productCombo\": null,\n" +
                "      \"user\": null\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 6,\n" +
                "      \"userId\": 1,\n" +
                "      \"orderNumber\": \"string\",\n" +
                "      \"productComboId\": 1,\n" +
                "      \"remark\": \"string11\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-19T08:06:17.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-19T08:06:17.000+0000\",\n" +
                "      \"remainTime\": 0,\n" +
                "      \"productCombo\": null,\n" +
                "      \"user\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pageNum\": 1,\n" +
                "  \"pageSize\": 10,\n" +
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
                "  \"userId\": 1,\n" +
                "  \"orderNumber\": \"string1\",\n" +
                "  \"productComboId\": 1,\n" +
                "  \"remark\": \"string\",\n" +
                "  \"status\": 1,\n" +
                "  \"createdAt\": \"2019-07-17T03:28:48.000+0000\",\n" +
                "  \"updatedAt\": \"2019-07-16T18:44:50.000+0000\",\n" +
                "  \"remainTime\": 0,\n" +
                "  \"productCombo\": null,\n" +
                "  \"user\": null\n" +
                "}";
    }

    //通过电话号码查询用户套餐列表（多表查询)
    public String listByUserTel() {
        return "{\n" +
                "  \"total\": 4,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"userId\": 0,\n" +
                "      \"orderNumber\": \"string1\",\n" +
                "      \"productComboId\": 0,\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-17T03:28:48.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-16T18:44:50.000+0000\",\n" +
                "      \"remainTime\": -1,\n" +
                "      \"productCombo\": {\n" +
                "        \"id\": 1,\n" +
                "        \"productId\": 1,\n" +
                "        \"name\": \"string\",\n" +
                "        \"time\": 30,\n" +
                "        \"price\": 10,\n" +
                "        \"remark\": \"string\",\n" +
                "        \"status\": 0,\n" +
                "        \"createdAt\": \"2019-07-16T18:26:37.000+0000\",\n" +
                "        \"updatedAt\": \"2019-07-16T18:26:37.000+0000\",\n" +
                "        \"product\": {\n" +
                "          \"id\": 1,\n" +
                "          \"name\": \"string\",\n" +
                "          \"versionName\": \"string\",\n" +
                "          \"logo\": \"string\",\n" +
                "          \"remark\": \"string\",\n" +
                "          \"status\": 2,\n" +
                "          \"createdAt\": \"2019-07-16T18:07:02.000+0000\",\n" +
                "          \"updatedAt\": \"2019-07-16T17:48:45.000+0000\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"user\": {\n" +
                "        \"id\": 1,\n" +
                "        \"username\": \"xivin\",\n" +
                "        \"tel\": \"13556559840\",\n" +
                "        \"password\": null,\n" +
                "        \"status\": 1,\n" +
                "        \"createdAt\": \"2019-07-17T19:12:55.000+0000\",\n" +
                "        \"updatedAt\": \"1979-12-31T16:00:01.000+0000\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"userId\": 0,\n" +
                "      \"orderNumber\": \"string2\",\n" +
                "      \"productComboId\": 0,\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 2,\n" +
                "      \"createdAt\": \"2019-07-17T03:28:50.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-16T18:45:47.000+0000\",\n" +
                "      \"remainTime\": 27,\n" +
                "      \"productCombo\": {\n" +
                "        \"id\": 1,\n" +
                "        \"productId\": 1,\n" +
                "        \"name\": \"string\",\n" +
                "        \"time\": 30,\n" +
                "        \"price\": 10,\n" +
                "        \"remark\": \"string\",\n" +
                "        \"status\": 0,\n" +
                "        \"createdAt\": \"2019-07-16T18:26:37.000+0000\",\n" +
                "        \"updatedAt\": \"2019-07-16T18:26:37.000+0000\",\n" +
                "        \"product\": {\n" +
                "          \"id\": 1,\n" +
                "          \"name\": \"string\",\n" +
                "          \"versionName\": \"string\",\n" +
                "          \"logo\": \"string\",\n" +
                "          \"remark\": \"string\",\n" +
                "          \"status\": 2,\n" +
                "          \"createdAt\": \"2019-07-16T18:07:02.000+0000\",\n" +
                "          \"updatedAt\": \"2019-07-16T17:48:45.000+0000\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"user\": {\n" +
                "        \"id\": 1,\n" +
                "        \"username\": \"xivin\",\n" +
                "        \"tel\": \"13556559840\",\n" +
                "        \"password\": null,\n" +
                "        \"status\": 1,\n" +
                "        \"createdAt\": \"2019-07-17T19:12:55.000+0000\",\n" +
                "        \"updatedAt\": \"1979-12-31T16:00:01.000+0000\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 5,\n" +
                "      \"userId\": 0,\n" +
                "      \"orderNumber\": \"string\",\n" +
                "      \"productComboId\": 0,\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 0,\n" +
                "      \"createdAt\": \"2019-07-18T12:19:57.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-18T12:19:57.000+0000\",\n" +
                "      \"remainTime\": -1,\n" +
                "      \"productCombo\": {\n" +
                "        \"id\": 1,\n" +
                "        \"productId\": 1,\n" +
                "        \"name\": \"string\",\n" +
                "        \"time\": 30,\n" +
                "        \"price\": 10,\n" +
                "        \"remark\": \"string\",\n" +
                "        \"status\": 0,\n" +
                "        \"createdAt\": \"2019-07-16T18:26:37.000+0000\",\n" +
                "        \"updatedAt\": \"2019-07-16T18:26:37.000+0000\",\n" +
                "        \"product\": {\n" +
                "          \"id\": 1,\n" +
                "          \"name\": \"string\",\n" +
                "          \"versionName\": \"string\",\n" +
                "          \"logo\": \"string\",\n" +
                "          \"remark\": \"string\",\n" +
                "          \"status\": 2,\n" +
                "          \"createdAt\": \"2019-07-16T18:07:02.000+0000\",\n" +
                "          \"updatedAt\": \"2019-07-16T17:48:45.000+0000\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"user\": {\n" +
                "        \"id\": 1,\n" +
                "        \"username\": \"xivin\",\n" +
                "        \"tel\": \"13556559840\",\n" +
                "        \"password\": null,\n" +
                "        \"status\": 1,\n" +
                "        \"createdAt\": \"2019-07-17T19:12:55.000+0000\",\n" +
                "        \"updatedAt\": \"1979-12-31T16:00:01.000+0000\"\n" +
                "      }\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 6,\n" +
                "      \"userId\": 0,\n" +
                "      \"orderNumber\": \"string\",\n" +
                "      \"productComboId\": 0,\n" +
                "      \"remark\": \"string11\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-19T08:06:17.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-19T08:06:17.000+0000\",\n" +
                "      \"remainTime\": 29,\n" +
                "      \"productCombo\": {\n" +
                "        \"id\": 1,\n" +
                "        \"productId\": 1,\n" +
                "        \"name\": \"string\",\n" +
                "        \"time\": 30,\n" +
                "        \"price\": 10,\n" +
                "        \"remark\": \"string\",\n" +
                "        \"status\": 0,\n" +
                "        \"createdAt\": \"2019-07-16T18:26:37.000+0000\",\n" +
                "        \"updatedAt\": \"2019-07-16T18:26:37.000+0000\",\n" +
                "        \"product\": {\n" +
                "          \"id\": 1,\n" +
                "          \"name\": \"string\",\n" +
                "          \"versionName\": \"string\",\n" +
                "          \"logo\": \"string\",\n" +
                "          \"remark\": \"string\",\n" +
                "          \"status\": 2,\n" +
                "          \"createdAt\": \"2019-07-16T18:07:02.000+0000\",\n" +
                "          \"updatedAt\": \"2019-07-16T17:48:45.000+0000\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"user\": {\n" +
                "        \"id\": 1,\n" +
                "        \"username\": \"xivin\",\n" +
                "        \"tel\": \"13556559840\",\n" +
                "        \"password\": null,\n" +
                "        \"status\": 1,\n" +
                "        \"createdAt\": \"2019-07-17T19:12:55.000+0000\",\n" +
                "        \"updatedAt\": \"1979-12-31T16:00:01.000+0000\"\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pageNum\": 1,\n" +
                "  \"pageSize\": 10,\n" +
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
}
