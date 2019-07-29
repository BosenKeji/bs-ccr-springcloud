package cn.bosenkeji.result.mock;

/**
 * @Author xivinChen
 * @create 2019/7/19 16:47
 */
public class TradePlatformApiBindProductComboControllerResultMock {

    public String getListByUserId(){
        return "{\n" +
                "  \"total\": 1,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"userId\": 1,\n" +
                "      \"tradePlatformApiId\": 1,\n" +
                "      \"userProductComboId\": 1,\n" +
                "      \"createdAt\": \"2019-07-27T03:37:50.000+0000\",\n" +
                "      \"updatedAt\": \"1970-01-01T00:00:01.000+0000\",\n" +
                "      \"tradePlatformApi\": {\n" +
                "        \"id\": 1,\n" +
                "        \"userId\": 1,\n" +
                "        \"tradePlatformId\": 1,\n" +
                "        \"sign\": \"1\",\n" +
                "        \"accessKey\": \"554gier3ibn\",\n" +
                "        \"secretKey\": \"24254gt4q3\",\n" +
                "        \"nickname\": \"董明珠\",\n" +
                "        \"status\": 1,\n" +
                "        \"createdAt\": \"2019-07-27T03:36:35.000+0000\",\n" +
                "        \"updatedAt\": \"1970-01-01T00:00:01.000+0000\",\n" +
                "        \"tradePlatform\": {\n" +
                "          \"id\": 1,\n" +
                "          \"name\": \"huobi\",\n" +
                "          \"logo\": \"/jiaoyipijngtai/logo.img\",\n" +
                "          \"status\": 1,\n" +
                "          \"createdAt\": \"2019-07-27T03:35:14.000+0000\",\n" +
                "          \"updatedAt\": \"1970-01-01T00:00:01.000+0000\",\n" +
                "          \"tradePlatformApi\": null,\n" +
                "          \"coinPairs\": null\n" +
                "        }\n" +
                "      },\n" +
                "      \"userProductCombo\": {\n" +
                "        \"id\": 1,\n" +
                "        \"userId\": 0,\n" +
                "        \"orderNumber\": \"20161022440844\",\n" +
                "        \"productComboId\": 0,\n" +
                "        \"remark\": \"联系电话：123565\",\n" +
                "        \"status\": 1,\n" +
                "        \"createdAt\": \"2019-07-27T03:39:28.000+0000\",\n" +
                "        \"updatedAt\": \"2019-07-16T18:44:50.000+0000\",\n" +
                "        \"remainTime\": 0,\n" +
                "        \"productCombo\": {\n" +
                "          \"id\": 1,\n" +
                "          \"productId\": 1,\n" +
                "          \"name\": \"一个月\",\n" +
                "          \"time\": 30,\n" +
                "          \"price\": 100,\n" +
                "          \"remark\": \"注意已有产品时不能部署\",\n" +
                "          \"status\": 1,\n" +
                "          \"createdAt\": \"2019-07-27T03:40:59.000+0000\",\n" +
                "          \"updatedAt\": \"2019-07-25T12:02:59.000+0000\",\n" +
                "          \"product\": {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"huobi\",\n" +
                "            \"versionName\": \"key houbi\",\n" +
                "            \"logo\": \"sgeoinlogh\",\n" +
                "            \"remark\": \"gewg\",\n" +
                "            \"status\": 1,\n" +
                "            \"createdAt\": \"2019-07-26T04:53:44.000+0000\",\n" +
                "            \"updatedAt\": \"2019-07-26T12:47:58.000+0000\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"user\": {\n" +
                "          \"id\": 1,\n" +
                "          \"username\": \"xivin\",\n" +
                "          \"tel\": \"13556559840\",\n" +
                "          \"password\": null,\n" +
                "          \"status\": 1,\n" +
                "          \"createdAt\": \"2019-07-17T19:12:55.000+0000\",\n" +
                "          \"updatedAt\": \"1979-12-31T16:00:01.000+0000\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pageNum\": 1,\n" +
                "  \"pageSize\": 10,\n" +
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


    public String getNoBindTradePlatformApiListByUserId() {
        return "{\n" +
                "  \"total\": 1,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"userId\": 1,\n" +
                "      \"tradePlatformId\": 2,\n" +
                "      \"sign\": \"1\",\n" +
                "      \"accessKey\": \"5644grg4g\",\n" +
                "      \"secretKey\": \"213234gg\",\n" +
                "      \"nickname\": \"俞敏洪\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-27T03:36:44.000+0000\",\n" +
                "      \"updatedAt\": \"1970-01-01T00:00:01.000+0000\",\n" +
                "      \"tradePlatform\": null\n" +
                "    }\n" +
                "  ],\n" +
                "  \"pageNum\": 1,\n" +
                "  \"pageSize\": 10,\n" +
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

    public String getNoBindUserProductComboListByUserId() {
        return "{\n" +
                "  \"total\": 9,\n" +
                "  \"list\": [\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"userId\": 0,\n" +
                "      \"orderNumber\": \"208125156872112\",\n" +
                "      \"productComboId\": 0,\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-27T03:38:49.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-16T18:45:47.000+0000\",\n" +
                "      \"remainTime\": 0,\n" +
                "      \"productCombo\": {\n" +
                "        \"id\": 1,\n" +
                "        \"productId\": 1,\n" +
                "        \"name\": \"一个月\",\n" +
                "        \"time\": 30,\n" +
                "        \"price\": 100,\n" +
                "        \"remark\": \"注意已有产品时不能部署\",\n" +
                "        \"status\": 1,\n" +
                "        \"createdAt\": \"2019-07-27T03:40:59.000+0000\",\n" +
                "        \"updatedAt\": \"2019-07-25T12:02:59.000+0000\",\n" +
                "        \"product\": {\n" +
                "          \"id\": 1,\n" +
                "          \"name\": \"huobi\",\n" +
                "          \"versionName\": \"key houbi\",\n" +
                "          \"logo\": \"sgeoinlogh\",\n" +
                "          \"remark\": \"gewg\",\n" +
                "          \"status\": 1,\n" +
                "          \"createdAt\": \"2019-07-26T04:53:44.000+0000\",\n" +
                "          \"updatedAt\": \"2019-07-26T12:47:58.000+0000\"\n" +
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
                "      \"remainTime\": 0,\n" +
                "      \"productCombo\": {\n" +
                "        \"id\": 1,\n" +
                "        \"productId\": 1,\n" +
                "        \"name\": \"一个月\",\n" +
                "        \"time\": 30,\n" +
                "        \"price\": 100,\n" +
                "        \"remark\": \"注意已有产品时不能部署\",\n" +
                "        \"status\": 1,\n" +
                "        \"createdAt\": \"2019-07-27T03:40:59.000+0000\",\n" +
                "        \"updatedAt\": \"2019-07-25T12:02:59.000+0000\",\n" +
                "        \"product\": {\n" +
                "          \"id\": 1,\n" +
                "          \"name\": \"huobi\",\n" +
                "          \"versionName\": \"key houbi\",\n" +
                "          \"logo\": \"sgeoinlogh\",\n" +
                "          \"remark\": \"gewg\",\n" +
                "          \"status\": 1,\n" +
                "          \"createdAt\": \"2019-07-26T04:53:44.000+0000\",\n" +
                "          \"updatedAt\": \"2019-07-26T12:47:58.000+0000\"\n" +
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
                "      \"remainTime\": 0,\n" +
                "      \"productCombo\": {\n" +
                "        \"id\": 1,\n" +
                "        \"productId\": 1,\n" +
                "        \"name\": \"一个月\",\n" +
                "        \"time\": 30,\n" +
                "        \"price\": 100,\n" +
                "        \"remark\": \"注意已有产品时不能部署\",\n" +
                "        \"status\": 1,\n" +
                "        \"createdAt\": \"2019-07-27T03:40:59.000+0000\",\n" +
                "        \"updatedAt\": \"2019-07-25T12:02:59.000+0000\",\n" +
                "        \"product\": {\n" +
                "          \"id\": 1,\n" +
                "          \"name\": \"huobi\",\n" +
                "          \"versionName\": \"key houbi\",\n" +
                "          \"logo\": \"sgeoinlogh\",\n" +
                "          \"remark\": \"gewg\",\n" +
                "          \"status\": 1,\n" +
                "          \"createdAt\": \"2019-07-26T04:53:44.000+0000\",\n" +
                "          \"updatedAt\": \"2019-07-26T12:47:58.000+0000\"\n" +
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
                "      \"id\": 7,\n" +
                "      \"userId\": 0,\n" +
                "      \"orderNumber\": \"xini\",\n" +
                "      \"productComboId\": 0,\n" +
                "      \"remark\": \"bexin\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-23T06:10:44.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-23T06:10:44.000+0000\",\n" +
                "      \"remainTime\": 0,\n" +
                "      \"productCombo\": {\n" +
                "        \"id\": 3,\n" +
                "        \"productId\": 2,\n" +
                "        \"name\": \"一年\",\n" +
                "        \"time\": 360,\n" +
                "        \"price\": 888,\n" +
                "        \"remark\": \"注意已有产品时不能部署\",\n" +
                "        \"status\": 1,\n" +
                "        \"createdAt\": \"2019-07-27T03:42:12.000+0000\",\n" +
                "        \"updatedAt\": \"2019-07-16T18:30:41.000+0000\",\n" +
                "        \"product\": {\n" +
                "          \"id\": 2,\n" +
                "          \"name\": \"string11\",\n" +
                "          \"versionName\": \"string\",\n" +
                "          \"logo\": \"string\",\n" +
                "          \"remark\": \"string111\",\n" +
                "          \"status\": 1,\n" +
                "          \"createdAt\": \"2019-07-24T17:47:48.000+0000\",\n" +
                "          \"updatedAt\": \"2019-07-25T01:42:03.000+0000\"\n" +
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
                "      \"id\": 8,\n" +
                "      \"userId\": 0,\n" +
                "      \"orderNumber\": \"ke\",\n" +
                "      \"productComboId\": 0,\n" +
                "      \"remark\": \"ke\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-23T08:07:10.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-23T08:07:10.000+0000\",\n" +
                "      \"remainTime\": 0,\n" +
                "      \"productCombo\": {\n" +
                "        \"id\": 1,\n" +
                "        \"productId\": 1,\n" +
                "        \"name\": \"一个月\",\n" +
                "        \"time\": 30,\n" +
                "        \"price\": 100,\n" +
                "        \"remark\": \"注意已有产品时不能部署\",\n" +
                "        \"status\": 1,\n" +
                "        \"createdAt\": \"2019-07-27T03:40:59.000+0000\",\n" +
                "        \"updatedAt\": \"2019-07-25T12:02:59.000+0000\",\n" +
                "        \"product\": {\n" +
                "          \"id\": 1,\n" +
                "          \"name\": \"huobi\",\n" +
                "          \"versionName\": \"key houbi\",\n" +
                "          \"logo\": \"sgeoinlogh\",\n" +
                "          \"remark\": \"gewg\",\n" +
                "          \"status\": 1,\n" +
                "          \"createdAt\": \"2019-07-26T04:53:44.000+0000\",\n" +
                "          \"updatedAt\": \"2019-07-26T12:47:58.000+0000\"\n" +
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
                "      \"id\": 14,\n" +
                "      \"userId\": 0,\n" +
                "      \"orderNumber\": \"string\",\n" +
                "      \"productComboId\": 0,\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-24T06:54:12.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-24T06:54:12.000+0000\",\n" +
                "      \"remainTime\": 6,\n" +
                "      \"productCombo\": {\n" +
                "        \"id\": 9,\n" +
                "        \"productId\": 3,\n" +
                "        \"name\": \"string9\",\n" +
                "        \"time\": 10,\n" +
                "        \"price\": 0,\n" +
                "        \"remark\": \"string9\",\n" +
                "        \"status\": 2,\n" +
                "        \"createdAt\": \"2019-07-23T22:59:29.000+0000\",\n" +
                "        \"updatedAt\": \"2019-07-23T06:07:30.000+0000\",\n" +
                "        \"product\": {\n" +
                "          \"id\": 3,\n" +
                "          \"name\": \"string\",\n" +
                "          \"versionName\": \"string\",\n" +
                "          \"logo\": \"string\",\n" +
                "          \"remark\": \"beizhu\",\n" +
                "          \"status\": 0,\n" +
                "          \"createdAt\": \"2019-07-23T02:32:35.000+0000\",\n" +
                "          \"updatedAt\": \"2019-07-23T02:32:35.000+0000\"\n" +
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
                "      \"id\": 17,\n" +
                "      \"userId\": 0,\n" +
                "      \"orderNumber\": \"string\",\n" +
                "      \"productComboId\": 0,\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-25T02:01:17.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-25T02:01:17.000+0000\",\n" +
                "      \"remainTime\": 27,\n" +
                "      \"productCombo\": {\n" +
                "        \"id\": 1,\n" +
                "        \"productId\": 1,\n" +
                "        \"name\": \"一个月\",\n" +
                "        \"time\": 30,\n" +
                "        \"price\": 100,\n" +
                "        \"remark\": \"注意已有产品时不能部署\",\n" +
                "        \"status\": 1,\n" +
                "        \"createdAt\": \"2019-07-27T03:40:59.000+0000\",\n" +
                "        \"updatedAt\": \"2019-07-25T12:02:59.000+0000\",\n" +
                "        \"product\": {\n" +
                "          \"id\": 1,\n" +
                "          \"name\": \"huobi\",\n" +
                "          \"versionName\": \"key houbi\",\n" +
                "          \"logo\": \"sgeoinlogh\",\n" +
                "          \"remark\": \"gewg\",\n" +
                "          \"status\": 1,\n" +
                "          \"createdAt\": \"2019-07-26T04:53:44.000+0000\",\n" +
                "          \"updatedAt\": \"2019-07-26T12:47:58.000+0000\"\n" +
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
                "      \"id\": 18,\n" +
                "      \"userId\": 0,\n" +
                "      \"orderNumber\": \"string\",\n" +
                "      \"productComboId\": 0,\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-25T02:55:25.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-25T02:55:25.000+0000\",\n" +
                "      \"remainTime\": 0,\n" +
                "      \"productCombo\": {\n" +
                "        \"id\": 1,\n" +
                "        \"productId\": 1,\n" +
                "        \"name\": \"一个月\",\n" +
                "        \"time\": 30,\n" +
                "        \"price\": 100,\n" +
                "        \"remark\": \"注意已有产品时不能部署\",\n" +
                "        \"status\": 1,\n" +
                "        \"createdAt\": \"2019-07-27T03:40:59.000+0000\",\n" +
                "        \"updatedAt\": \"2019-07-25T12:02:59.000+0000\",\n" +
                "        \"product\": {\n" +
                "          \"id\": 1,\n" +
                "          \"name\": \"huobi\",\n" +
                "          \"versionName\": \"key houbi\",\n" +
                "          \"logo\": \"sgeoinlogh\",\n" +
                "          \"remark\": \"gewg\",\n" +
                "          \"status\": 1,\n" +
                "          \"createdAt\": \"2019-07-26T04:53:44.000+0000\",\n" +
                "          \"updatedAt\": \"2019-07-26T12:47:58.000+0000\"\n" +
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
                "      \"id\": 30,\n" +
                "      \"userId\": 0,\n" +
                "      \"orderNumber\": \"string\",\n" +
                "      \"productComboId\": 0,\n" +
                "      \"remark\": \"string\",\n" +
                "      \"status\": 1,\n" +
                "      \"createdAt\": \"2019-07-26T03:09:19.000+0000\",\n" +
                "      \"updatedAt\": \"2019-07-26T08:56:07.000+0000\",\n" +
                "      \"remainTime\": 8,\n" +
                "      \"productCombo\": {\n" +
                "        \"id\": 11,\n" +
                "        \"productId\": 4,\n" +
                "        \"name\": \"strin1g\",\n" +
                "        \"time\": 10,\n" +
                "        \"price\": 0,\n" +
                "        \"remark\": \"string\",\n" +
                "        \"status\": 1,\n" +
                "        \"createdAt\": \"2019-07-23T22:59:32.000+0000\",\n" +
                "        \"updatedAt\": \"2019-07-24T05:09:40.000+0000\",\n" +
                "        \"product\": {\n" +
                "          \"id\": 4,\n" +
                "          \"name\": \"string\",\n" +
                "          \"versionName\": \"string\",\n" +
                "          \"logo\": \"string\",\n" +
                "          \"remark\": \"beizhu\",\n" +
                "          \"status\": 1,\n" +
                "          \"createdAt\": \"2019-07-22T19:25:27.000+0000\",\n" +
                "          \"updatedAt\": \"2019-07-23T03:19:46.000+0000\"\n" +
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
                "  \"pageSize\": 9,\n" +
                "  \"size\": 9,\n" +
                "  \"startRow\": 0,\n" +
                "  \"endRow\": 8,\n" +
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
