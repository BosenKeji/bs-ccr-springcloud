package cn.bosenkeji.result.mock;

public class CoinPairDealControllerResultMock {

    public String findCoinPairDealByUserId() {
        return "{\n" +
                "    \"total\": 3,\n" +
                "    \"list\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"coinPartnerChoicId\": 1,\n" +
                "            \"type\": 1,\n" +
                "            \"quantity\": 10,\n" +
                "            \"status\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"coinPartnerChoicId\": 2,\n" +
                "            \"type\": 1,\n" +
                "            \"quantity\": 20,\n" +
                "            \"status\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 3,\n" +
                "            \"coinPartnerChoicId\": 2,\n" +
                "            \"type\": 2,\n" +
                "            \"quantity\": 15,\n" +
                "            \"status\": 1\n" +
                "        }\n" +
                "    ],\n" +
                "    \"pageNum\": 1,\n" +
                "    \"pageSize\": 3,\n" +
                "    \"size\": 3,\n" +
                "    \"startRow\": 0,\n" +
                "    \"endRow\": 2,\n" +
                "    \"pages\": 1,\n" +
                "    \"prePage\": 0,\n" +
                "    \"nextPage\": 0,\n" +
                "    \"isFirstPage\": true,\n" +
                "    \"isLastPage\": true,\n" +
                "    \"hasPreviousPage\": false,\n" +
                "    \"hasNextPage\": false,\n" +
                "    \"navigatePages\": 8,\n" +
                "    \"navigatepageNums\": [\n" +
                "        1\n" +
                "    ],\n" +
                "    \"navigateFirstPage\": 1,\n" +
                "    \"navigateLastPage\": 1\n" +
                "}";
    }


    public String findCoinPairDealByUserIdAndChoicId() {
        return "{\n" +
                "    \"total\": 2,\n" +
                "    \"list\": [\n" +
                "        {\n" +
                "            \"id\": 4,\n" +
                "            \"coinPartnerChoicId\": 3,\n" +
                "            \"type\": 1,\n" +
                "            \"quantity\": 30,\n" +
                "            \"status\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 5,\n" +
                "            \"coinPartnerChoicId\": 3,\n" +
                "            \"type\": 2,\n" +
                "            \"quantity\": 10,\n" +
                "            \"status\": 1\n" +
                "        }\n" +
                "    ],\n" +
                "    \"pageNum\": 1,\n" +
                "    \"pageSize\": 2,\n" +
                "    \"size\": 2,\n" +
                "    \"startRow\": 0,\n" +
                "    \"endRow\": 1,\n" +
                "    \"pages\": 1,\n" +
                "    \"prePage\": 0,\n" +
                "    \"nextPage\": 0,\n" +
                "    \"isFirstPage\": true,\n" +
                "    \"isLastPage\": true,\n" +
                "    \"hasPreviousPage\": false,\n" +
                "    \"hasNextPage\": false,\n" +
                "    \"navigatePages\": 8,\n" +
                "    \"navigatepageNums\": [\n" +
                "        1\n" +
                "    ],\n" +
                "    \"navigateFirstPage\": 1,\n" +
                "    \"navigateLastPage\": 1\n" +
                "}";
    }

    public String findCoinPairDealByUserIdAndType() {
        return "{\n" +
                "    \"total\": 3,\n" +
                "    \"list\": [\n" +
                "        {\n" +
                "            \"id\": 1,\n" +
                "            \"coinPartnerChoicId\": 1,\n" +
                "            \"type\": 1,\n" +
                "            \"quantity\": 10,\n" +
                "            \"status\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 2,\n" +
                "            \"coinPartnerChoicId\": 2,\n" +
                "            \"type\": 1,\n" +
                "            \"quantity\": 20,\n" +
                "            \"status\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 4,\n" +
                "            \"coinPartnerChoicId\": 3,\n" +
                "            \"type\": 1,\n" +
                "            \"quantity\": 30,\n" +
                "            \"status\": 1\n" +
                "        }\n" +
                "    ],\n" +
                "    \"pageNum\": 1,\n" +
                "    \"pageSize\": 3,\n" +
                "    \"size\": 3,\n" +
                "    \"startRow\": 0,\n" +
                "    \"endRow\": 2,\n" +
                "    \"pages\": 1,\n" +
                "    \"prePage\": 0,\n" +
                "    \"nextPage\": 0,\n" +
                "    \"isFirstPage\": true,\n" +
                "    \"isLastPage\": true,\n" +
                "    \"hasPreviousPage\": false,\n" +
                "    \"hasNextPage\": false,\n" +
                "    \"navigatePages\": 8,\n" +
                "    \"navigatepageNums\": [\n" +
                "        1\n" +
                "    ],\n" +
                "    \"navigateFirstPage\": 1,\n" +
                "    \"navigateLastPage\": 1\n" +
                "}";
    }

    public String updateCoinPairDealStartsById() {
        return "true";
    }

    public String countCoinPair() {
        return "3";
    }

    public String countCoinPairDeal() {
        return "2";
    }

    public String insertCoinPairDealBySelective() {
        return "true";
    }

    public String deleteCoinPairDealByPrimaryKey() { return "true"; }

    public String deleteBatchCoinPairDealByUserIdAndChoicId() { return "true"; }
}
