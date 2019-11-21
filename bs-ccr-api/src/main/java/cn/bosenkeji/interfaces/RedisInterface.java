package cn.bosenkeji.interfaces;

    /**
     * FileName: RedisInterface
     * Author: xivin
     * Date: 2019-08-19 11:59
     * Description: redis key
    */

    public interface RedisInterface {

        //用户
        String USER_REDIS_ID_KEY="ccr:user:id";
        String USER_REDIS_USERNAME_KEY="ccr:user:username";

        //产品
        String PRODUCT_ID_KEY="ccr:product:id";
        String PRODUCT_LIST_KEY="ccr:product:list";
        String PRODUCT_LIST_STATUS_KEY="ccr:product:listByStatus";
        String PRODUCT_LIST_IDS="ccr:product:listByIds";

        //产品套餐
        String PRODUCT_COMBO_ID_KEY="ccr:productCombo:id";
        String PRODUCT_COMBO_LIST_KEY="ccr:productCombo:list";
        String PRODUCT_COMBO_LIST_PID_KEY = "ccr:productCombo:listByPid";
        String PRODUCT_COMBO_LIST_PID_STATUS_KEY="ccr:productCombo:listByPidAndStatus";
        String PRODUCT_COMBO_LIST_PID_STATUS_PAGE_KEY="ccr:productCombo:listByPidAndStatusWithPage";

        //用户套餐
        String USER_COMBO_ID_KEY="ccr:userCombo:id";

        //套餐时长
        String COMBO_DAY_ID_KEY="ccr:comboDay:id";
        String COMBO_DAY_LIST_UPC_ID_KEY="ccr:comboDay:listByUpcId";
        String COMBO_DAY_LIST_TEL_KEY="ccr:comboDay:listByTel";

        //策略相关
        String STRATEGY_ID_KEY="ccr:strategy:id";
        String STRATEGY_LIST_KEY="ccr:strategy:list";

        String STRATEGY_SEQUENCE_ID_KEY="ccr:strategySequence:id";
        String STRATEGY_SEQUENCE_LIST_KEY="ccr:strategySequence:list";

        //管理员相关
        String ADMIN_LIST_IDS_KEY="ccr:admin:listByIds";
        String ADMIN_ID_KEY="ccr:admin:id";

        //货币
        String COIN_ID_KEY="ccr:coin:id";
        String COIN_LIST_KEY="ccr:coin:list";

        //货币对
        String COIN_PAIR_ID_KEY="ccr:coinPair:id";
        String COIN_PAIR_LIST_KEY="ccr:coinPair:list";
        String COIN_PAIR_LIST_IDS_KEY="ccr:coinPair:listByIds";
        String COIN_PAIR_LIST_NAME_KEY="ccr:coinPair:listByName";

        // 货币排序
        String COIN_SORT_ID_KEY="ccr:coinSort:id";
        String COIN_SORT_LIST_KEY="ccr:coinSort:list";
        String COIN_SORT_LIST_TPID_KEY="ccr:coinSort:listByTpId";

        //货币对货币
        String COIN_PAIR_COIN_ID_KEY="ccr:coinPairCoin:id";
        String COIN_PAIR_COIN_LIST_KEY="ccr:coinPairCoin:list";
        String COIN_PAIR_COIN_LIST_CID_KEY="ccr:coinPairCoin:listByCid";

        //自选币
        String COIN_PAIR_CHOICE_ID_KEY="ccr:coinPairChoice:id";
        String COIN_PAIR_CHOICE_LIST_KEY="ccr:coinPairChoice:list";

        String COIN_PAIR_CHOICE_ATTRIBUTE_ID_KEY="ccr:coinPairChoiceAttribute:id";
        String COIN_PAIR_CHOICE_ATTRIBUTE_LIST_KEY="ccr:coinPairChoiceAttribute:list";

        String COIN_PAIR_CHOICE_ATTRIBUTE_CUSTOM_ID_KEY="ccr:coinPairChoiceAttributeCustom:id";
        String COIN_PAIR_CHOICE_ATTRIBUTE_CUSTOM_LIST_KEY="ccr:coinPairChoiceAttributeCustom:list";

        String COIN_PAIR_DEAL_ID_KEY="ccr:coinPairDeal:id";
        String COIN_PAIR_DEAL_LIST_KEY="ccr:coinPairDeal:list";

        //交易平台
        String TRADE_PLATFORM_ID_KEY="ccr:tradePlatform:id";
        String TRADE_PLATFORM_LIST_KEY="ccr:tradePlatform:list";

        String TRADE_PLATFORM_API_ID_KEY="ccr:tradePlatformApi:id";
        String TRADE_PLATFORM_API_LIST_KEY="ccr:tradePlatformApi:list";

        String TRADE_PLATFORM_COIN_ID_KEY="ccr:tradePlatformCoinPair:id";
        String TRADE_PLATFORM_COIN_LIST_KEY="ccr:tradePlatformCoinPair:list";

        String REASON_LIST_TYPE_KEY = "ccr:reason:listByType";
        String REASON_ID_KEY = "ccr:reason:id";



}
