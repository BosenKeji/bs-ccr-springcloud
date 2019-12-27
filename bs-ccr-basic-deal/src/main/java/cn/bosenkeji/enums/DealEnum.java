package cn.bosenkeji.enums;

/**
 *
 * 交易的枚举
 *
 * @author hjh
 * @date 2019-12-27
 */

public class DealEnum {

    /**
     * 通用参数
     * SYMBOL：货币对名称
     * SIGN_ID：标识id
     * TRADE_STATUS：交易状态
     * POSITION_COST：持仓费用
     * POSITION_NUM：持仓数量
     */
    public static final String SYMBOL = "symbol";
    public static final String SIGN_ID = "signId";

    public static final String TRADE_STATUS = "trade_status";
    public static final String POSITION_COST = "position_cost";
    public static final String POSITION_NUM = "position_num";

    //买需要的参数
    /**
     * 买需要的参数
     * FINISHED_ORDER：已做单数
     * MAX_TRADE_ORDER：最大交易单数
     * STORE_SPLIT：建仓间隔
     *
     * BUY_VOLUME：买入量
     * FOLLOW_LOWER_RATIO：追踪下调比
     * FOLLOW_CALLBACK_RATIO：追踪回调比
     * FIRST_ORDER_PRICE：首单现价
     *
     */

    public static final String FINISHED_ORDER = "finished_order";
    public static final String MAX_TRADE_ORDER = "max_trade_order";
    public static final String STORE_SPLIT = "store_split";

    public static final String BUY_VOLUME = "buy_volume";
    public static final String FOLLOW_LOWER_RATIO = "follow_lower_ratio";
    public static final String FOLLOW_CALLBACK_RATIO = "follow_callback_ratio";
    public static final String FIRST_ORDER_PRICE = "first_order_price";

    /**
     * 卖需要的参数
     *
     * TARGET_PROFIT_PRICE：止盈金额
     * IS_STOP_PROFIT_TRACE：是否使用追踪止盈
     * TURN_DOWN_RATIO：追踪止盈触发比例
     * EMIT_RATIO：追踪止盈回调比例
     */
    public static final String TARGET_PROFIT_PRICE = "target_profit_price";
    public static final String IS_STOP_PROFIT_TRACE = "is_stop_profit_trace";
    public static final String TURN_DOWN_RATIO = "turn_down_ratio";

    public static final String EMIT_RATIO = "emit_ratio";

    public static final String TRADE_TYPE_BUY = "buy";
    public static final String TRADE_TYPE_SELL = "sell";


    /**
     *
     * IS_TRIGGER_TRACE_STOP_PROFIT:是否触发追踪止盈
     * IS_FOLLOW_BUILD:是否触发追踪建仓
     *
     * MIN_AVERAGE_PRICE:最小拟买入均价
     * HISTORY_MAX_BENEFIT_RATIO:历史最高收益比
     * REAL_TIME_EARNING_RATIO:实时收益比
     *
     * TRIGGER_FOLLOW_BUILD_ORDER:触发追踪建仓的单数
     * TRIGGER_STOP_PROFIT_ORDER:触发追踪止盈的单数
     */
    public static final String IS_TRIGGER_TRACE_STOP_PROFIT = "is_trigger_trace_stop_profit";
    public static final String IS_FOLLOW_BUILD = "is_follow_build";

    public static final String MIN_AVERAGE_PRICE = "min_average_price";
    public static final String HISTORY_MAX_BENEFIT_RATIO = "history_max_benefit_ratio";
    public static final String REAL_TIME_EARNING_RATIO = "real_time_earning_ratio";

    public static final String TRIGGER_FOLLOW_BUILD_ORDER = "trigger_follow_build_order";
    public static final String TRIGGER_STOP_PROFIT_ORDER = "trigger_stop_profit_order";


    /**
     * SELL_PRICE:现价
     * BUY_DEEP：买入深度
     * SELL_DEEP：卖深度
     * PLATFORM_NAME：交易平台名称
     */
    public static final String SELL_PRICE = "sell";
    public static final String BUY_DEEP = "bids";
    public static final String SELL_DEEP = "asks";
    public static final String PLATFORM_NAME = "plantFormName";

    public static final String OKEX_PLATFORM_NAME = "okex";

    /**
     * 3代表停止买入
     */
    public static final Integer TRADE_STATUS_3 = 3;
}
