package cn.bosenkeji.utils;

import cn.bosenkeji.vo.DealParameter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;

/**
 * deal模块JSON解析
 *
 * 传入一个JSON字符串，解析出买入和卖出所需的参数
 *
 * @author hjh
 *
 **/

public class DealParameterParser {

    private JSONObject jsonObject;
    private String redisKey;

    public DealParameterParser(JSONObject jsonObject, String redisKey) {
        this.jsonObject = jsonObject;
        this.redisKey = redisKey;
    }

    //通用参数
    private static final String ACCESS_KEY = "accessKey";
    private static final String SECRET_KEY = "secretKey";
    private static final String SYMBOL = "symbol";

    private static final String CAN_SEND_MSG_2_NODE = "canSendMsg2Node";  //是否给node端发送消息

    private static final String POSITION_COST = "position_cost";  //持仓费用
    private static final String POSITION_NUM = "position_num";   //持仓数量

    //买需要的参数
    private static final String FINISHED_ORDER = "finished_order"; //已做单数
    private static final String MAX_TRADE_ORDER = "max_trade_order"; //最大交易单数
    private static final String STORE_SPLIT = "store_split"; //建仓间隔

    private static final String BUY_VOLUME = "buy_volume"; //买入量
    private static final String FOLLOW_LOWER_RATIO = "follow_lower_ratio"; //追踪下调比
    private static final String FOLLOW_CALLBACK_RATIO = "follow_callback_ratio"; //追踪回调比

    static final String MIN_AVERAGE_PRICE = "min_averagePrice"; //最小交易均价
    private static final String FIRST_ORDER_PRICE = "first_order_price"; //首单现价
    static final String IS_FOLLOW_BUILD = "isFollowBuild";

    //卖需要的参数
    private static final String TARGET_PROFIT_PRICE = "target_profit_price"; //止盈金额
    private static final String IS_USE_FOLLOW_TARGET_PROFIT = "is_use_follow_target_profit"; //是否使用追踪止盈
    private static final String TURN_DOWN_RATIO = "turn_down_ratio"; //追踪止盈触发比例

    private static final String EMIT_RATIO = "emit_ratio"; //追踪止盈回调比例
    static final String HISTORY_MAX_RISK_BENEFIT_RATIO = "history_max_riskBenefitRatio";
    static final String IS_TRIGGER_TRACE_STOP_PROFIT = "is_trigger_trace_stop_profit";

    /**
     *
     * 获取redis在交易中的参数
     *
     **/

    public DealParameter getDealParameter() {
        DealParameter parameter = new DealParameter();
        parameter.setRedisKey(redisKey);
        parameter.setJsonObject(jsonObject);

        parameter.setAccessKey(DealUtil.getString(jsonObject.get(ACCESS_KEY)));
        parameter.setSecretKey(DealUtil.getString(jsonObject.get(SECRET_KEY)));
        parameter.setSymbol(DealUtil.getString(jsonObject.get(SYMBOL)));

        parameter.setCanSendMsgToNode(DealUtil.getInteger(jsonObject.get(CAN_SEND_MSG_2_NODE)));
        parameter.setPositionCost(DealUtil.getDouble(jsonObject.get(POSITION_COST)));
        parameter.setPositionNum(DealUtil.getDouble(jsonObject.get(POSITION_NUM)));

        parameter.setFinishedOrder(DealUtil.getInteger(jsonObject.get(FINISHED_ORDER)));
        parameter.setMaxTradeOrder(DealUtil.getInteger(jsonObject.get(MAX_TRADE_ORDER)));
        parameter.setStoreSplit(DealUtil.getDouble(jsonObject.get(STORE_SPLIT)));

        // Map 特殊处理
        JSONObject jsonBuyVolume = (JSONObject) jsonObject.get(BUY_VOLUME);
        parameter.setBuyVolume(jsonBuyVolume);

        parameter.setFollowLowerRatio(DealUtil.getDouble(jsonObject.get(FOLLOW_LOWER_RATIO)));
        parameter.setFollowCallbackRatio(DealUtil.getDouble(jsonObject.get(FOLLOW_CALLBACK_RATIO)));

        parameter.setMinAveragePrice(DealUtil.getDouble(jsonObject.get(MIN_AVERAGE_PRICE)));
        parameter.setFirstOrderPrice(DealUtil.getDouble(jsonObject.get(FIRST_ORDER_PRICE)));
        parameter.setIsFollowBuild(DealUtil.getInteger(jsonObject.get(IS_FOLLOW_BUILD)));

        parameter.setTargetProfitPrice(DealUtil.getDouble(jsonObject.get(TARGET_PROFIT_PRICE)));

        parameter.setIsUseFollowTargetProfit(DealUtil.getInteger(jsonObject.get(IS_USE_FOLLOW_TARGET_PROFIT)));
        parameter.setTurnDownRatio(DealUtil.getDouble(jsonObject.get(TURN_DOWN_RATIO)));
        parameter.setEmitRatio(DealUtil.getDouble(jsonObject.get(EMIT_RATIO)));

        parameter.setHistoryMaxRiskBenefitRatio(DealUtil.getDouble(jsonObject.get(HISTORY_MAX_RISK_BENEFIT_RATIO)));
        parameter.setIsTriggerTraceStopProfit(DealUtil.getInteger(jsonObject.get(IS_TRIGGER_TRACE_STOP_PROFIT)));
        return parameter;
    }


}
