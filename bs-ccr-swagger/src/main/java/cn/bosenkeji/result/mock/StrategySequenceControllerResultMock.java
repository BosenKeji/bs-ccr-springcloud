package cn.bosenkeji.result.mock;

public class StrategySequenceControllerResultMock {

    public String insertStrategySequence() {
        return "true";
    }

    public String insertStrategySequenceValue() {
        return "true";
    }

    public String findAll() {
        return "{\n" +
                "    \"total\": 7,\n" +
                "    \"list\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"name\": \"倍投\",\n" +
                "            \"tip\": \"1，2，4，8，16...最经典的数列，收单较快，所需本金较多\",\n" +
                "            \"status\": 1,\n" +
                "            \"createdAt\": \"2019-07-15T22:09:01.000+0000\",\n" +
                "            \"updatedAt\": \"1970-01-01T00:00:01.000+0000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"name\": \"倍投PLUS\",\n" +
                "            \"tip\": \"1，3，7，15，31...数字增幅比倍投略大，收单速度更快，所需本金更多\",\n" +
                "            \"status\": 1,\n" +
                "            \"createdAt\": \"2019-07-15T22:09:23.000+0000\",\n" +
                "            \"updatedAt\": \"1970-01-01T00:00:01.000+0000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 3,\n" +
                "            \"name\": \"三倍\",\n" +
                "            \"tip\": \"1，3，9，27，81...三倍倍投数列收单速度最快，所需本金最多\",\n" +
                "            \"status\": 1,\n" +
                "            \"createdAt\": \"2019-07-15T22:23:47.000+0000\",\n" +
                "            \"updatedAt\": \"1970-01-01T00:00:01.000+0000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 4,\n" +
                "            \"name\": \"asd\",\n" +
                "            \"tip\": \"321\",\n" +
                "            \"status\": 1,\n" +
                "            \"createdAt\": \"2019-07-18T09:31:44.000+0000\",\n" +
                "            \"updatedAt\": \"2019-07-18T09:31:44.000+0000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 5,\n" +
                "            \"name\": \"asd\",\n" +
                "            \"tip\": \"321\",\n" +
                "            \"status\": 1,\n" +
                "            \"createdAt\": \"2019-07-18T09:57:48.000+0000\",\n" +
                "            \"updatedAt\": \"2019-07-18T09:57:48.000+0000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 6,\n" +
                "            \"name\": \"zzz\",\n" +
                "            \"tip\": \"1,2,3,4,5\",\n" +
                "            \"status\": 1,\n" +
                "            \"createdAt\": \"2019-07-19T02:14:29.000+0000\",\n" +
                "            \"updatedAt\": \"2019-07-19T02:14:29.000+0000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 7,\n" +
                "            \"name\": \"zzz\",\n" +
                "            \"tip\": \"1,2,3,4,5\",\n" +
                "            \"status\": 1,\n" +
                "            \"createdAt\": \"2019-07-19T08:34:13.000+0000\",\n" +
                "            \"updatedAt\": \"2019-07-19T08:34:13.000+0000\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"pageNum\": 0,\n" +
                "    \"pageSize\": 10,\n" +
                "    \"size\": 7,\n" +
                "    \"startRow\": 1,\n" +
                "    \"endRow\": 7,\n" +
                "    \"pages\": 1,\n" +
                "    \"prePage\": 0,\n" +
                "    \"nextPage\": 1,\n" +
                "    \"isFirstPage\": false,\n" +
                "    \"isLastPage\": false,\n" +
                "    \"hasPreviousPage\": false,\n" +
                "    \"hasNextPage\": true,\n" +
                "    \"navigatePages\": 8,\n" +
                "    \"navigatepageNums\": [\n" +
                "        1\n" +
                "    ],\n" +
                "    \"navigateFirstPage\": 1,\n" +
                "    \"navigateLastPage\": 1\n" +
                "}";
    }

    public String findSequenceByPrimaryKey() {
        return "{\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"倍投PLUS\",\n" +
                "    \"tip\": \"1，3，7，15，31...数字增幅比倍投略大，收单速度更快，所需本金更多\",\n" +
                "    \"status\": 1,\n" +
                "    \"value\": \"1,3,7,15,31,63,127,255,511,1023,2047,4095,8191,16383,32767,65535,131071,262143,524287,1048575\"\n" +
                "}";
    }

    public String getSequenceValueByStrategyId() {
        return "1,2,4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384,32768,65536,131072,262144,524288";
    }
}
