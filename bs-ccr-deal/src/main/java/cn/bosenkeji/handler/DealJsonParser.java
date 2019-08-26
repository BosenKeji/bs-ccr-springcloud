package cn.bosenkeji.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * deal模块JSON解析
 *
 * 传入一个JSON字符串，解析出买入和卖出所需的参数
 *
 * @author hjh
 *
 **/


public class DealJsonParser {


    /**
     *
     * 获取买卖的参数
     *
     * @param redisKey 该json字符串对应redis中的key
     * @param jsonObject redis中获取用户设置的信息json字符串
     * @return map 解析json字符串产生的map
     *
     **/

    private Map<String,Object> getBuyParameter(String redisKey, JSONObject jsonObject) {

        //通用参数
        String ACCESS_KEY = "accessKey";
        String SECRET_KEY = "secretKey";
        String SYMBOL = "symbol";

        String CAN_SEND_MSG_2_NODE = "canSendMsg2Node";  //是否给node端发送消息

        String POSITION_COST = "position_cost";  //持仓费用
        String POSITION_NUM = "position_num";   //持仓数量

        //买需要的参数
        String FINISHED_ORDER = "finished_order"; //已做单数
        String MAX_TRADE_ORDER = "max_trade_order"; //最大交易单数
        String STORE_SPLIT = "store_split"; //建仓间隔

        String BUY_VOLUME = "buy_volume"; //买入量
        String FOLLOW_LOWER_RATIO = "follow_lower_ratio"; //追踪下调比
        String FOLLOW_CALLBACK_RATIO = "follow_callback_ratio"; //追踪回调比

        String MIN_AVERAGEPRICE = "min_averagePrice"; //最小交易均价
        String FIRST_ORDER_PRICE = "first_order_price"; //首单现价

        //卖需要的参数
        String TARGET_PROFIT_PRICE = "target_profit_price"; //止盈金额
        String IS_USE_FOLLOW_TARGET_PROFIT = "is_use_follow_target_profit"; //是否使用追踪止盈
        String TURN_DOWN_RATIO = "turn_down_ratio"; //追踪止盈触发比例
        String EMIT_RATIO = "emit_ratio"; //追踪止盈回调比例



        Map<String,Object> buyParameterMap = new HashMap<>();


        Object accessKey = jsonObject.get(ACCESS_KEY);
        Object secretKey = jsonObject.get(SECRET_KEY);
        Object symbol = jsonObject.get(SYMBOL);

        Object canSendMsg2Node = jsonObject.get(CAN_SEND_MSG_2_NODE);
        Object positionCost = jsonObject.get(POSITION_COST);
        Object positionNum = jsonObject.get(POSITION_NUM);

        Object finishedOrder = jsonObject.get(FINISHED_ORDER);
        Object maxTradeOrder = jsonObject.get(MAX_TRADE_ORDER);
        Object storeSplit = jsonObject.get(STORE_SPLIT);

        Object buyVolume = jsonObject.get(BUY_VOLUME);
        Object followLowerRatio = jsonObject.get(FOLLOW_LOWER_RATIO);
        Object followCallbackRatio = jsonObject.get(FOLLOW_CALLBACK_RATIO);

        Object minAverageprice = jsonObject.get(MIN_AVERAGEPRICE);
        Object firstOrderPrice = jsonObject.get(FIRST_ORDER_PRICE);
        Object targetProfitPrice = jsonObject.get(TARGET_PROFIT_PRICE);

        Object isUseFollowTargetProfit = jsonObject.get(IS_USE_FOLLOW_TARGET_PROFIT);
        Object turnDownRatio = jsonObject.get(TURN_DOWN_RATIO);
        Object emitRatio = jsonObject.get(EMIT_RATIO);

        buyParameterMap.put("accessKey",accessKey);
        buyParameterMap.put("secretKey",secretKey);
        buyParameterMap.put("symbol",symbol);

        buyParameterMap.put("canSendMsg2Node",canSendMsg2Node);
        buyParameterMap.put("position_cost",positionCost);
        buyParameterMap.put("position_num",positionNum);

        buyParameterMap.put("finished_order",finishedOrder);
        buyParameterMap.put("max_trade_order",maxTradeOrder);
        buyParameterMap.put("store_split",storeSplit);

        buyParameterMap.put("buy_volume",buyVolume);
        buyParameterMap.put("follow_lower_ratio",followLowerRatio);
        buyParameterMap.put("follow_callback_ratio",followCallbackRatio);

        buyParameterMap.put("min_averagePrice",minAverageprice);
        buyParameterMap.put("first_order_price",firstOrderPrice);
        buyParameterMap.put("target_profit_price",targetProfitPrice);

        buyParameterMap.put("is_use_follow_target_profit",isUseFollowTargetProfit);
        buyParameterMap.put("turn_down_ratio",turnDownRatio);
        buyParameterMap.put("emit_ratio",emitRatio);
        return buyParameterMap;
    }
}
