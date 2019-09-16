package cn.bosenkeji.utils;

import cn.bosenkeji.vo.DealParameter;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * deal模块JSON解析
 *
 * 传入一个JSON字符串，解析出买入和卖出所需的参数
 *
 * @author hjh
 *
 **/

public class DealParameterParser {

    private static final Logger log = LoggerFactory.getLogger(DealParameterParser.class);

    private Map trade;

    public DealParameterParser(Map trade) {
        this.trade = trade;
    }

    //通用参数
    private static final String ACCESS_KEY = "accessKey";
    private static final String SECRET_KEY = "secretKey";
    private static final String SYMBOL = "symbol";

    private static final String TRADE_STATUS = "trade_status";  //是否给node端发送消息

    private static final String POSITION_COST = "position_cost";  //持仓费用
    private static final String POSITION_NUM = "position_num";   //持仓数量

    //买需要的参数
    private static final String FINISHED_ORDER = "finished_order"; //已做单数
    private static final String MAX_TRADE_ORDER = "max_trade_order"; //最大交易单数
    private static final String STORE_SPLIT = "store_split"; //建仓间隔

    private static final String BUY_VOLUME = "buy_volume"; //买入量
    private static final String FOLLOW_LOWER_RATIO = "follow_lower_ratio"; //追踪下调比
    private static final String FOLLOW_CALLBACK_RATIO = "follow_callback_ratio"; //追踪回调比

    private static final String FIRST_ORDER_PRICE = "first_order_price"; //首单现价

    //卖需要的参数
    private static final String TARGET_PROFIT_PRICE = "target_profit_price"; //止盈金额
    private static final String IS_STOP_PROFIT_TRACE = "is_stop_profit_trace"; //是否使用追踪止盈
    private static final String TURN_DOWN_RATIO = "turn_down_ratio"; //追踪止盈触发比例

    private static final String EMIT_RATIO = "emit_ratio"; //追踪止盈回调比例

    /**
     *
     * 获取redis在交易中的参数
     *
     **/

    public DealParameter getDealParameter() {
        DealParameter parameter = new DealParameter();
        parameter.setTrade(trade);

        parameter.setAccessKey(DealUtil.getString(trade.get(ACCESS_KEY)));
        parameter.setSecretKey(DealUtil.getString(trade.get(SECRET_KEY)));
        parameter.setSymbol(DealUtil.getString(trade.get(SYMBOL)));

        parameter.setTradeStatus(DealUtil.getInteger(trade.get(TRADE_STATUS)));
        parameter.setPositionCost(DealUtil.getDouble(trade.get(POSITION_COST)));
        parameter.setPositionNum(DealUtil.getDouble(trade.get(POSITION_NUM)));

        parameter.setFinishedOrder(DealUtil.getInteger(trade.get(FINISHED_ORDER)));
        parameter.setMaxTradeOrder(DealUtil.getInteger(trade.get(MAX_TRADE_ORDER)));
        parameter.setStoreSplit(DealUtil.getDouble(trade.get(STORE_SPLIT)));

        // Map 特殊处理
        Object s = trade.get(BUY_VOLUME);
        if (s != null) {
            String unescape = StringEscapeUtils.unescapeJava(s.toString());
            JSONObject jsonBuyVolume = JSONObject.parseObject(unescape);
            parameter.setBuyVolume(jsonBuyVolume);
        }

        parameter.setFollowLowerRatio(DealUtil.getDouble(trade.get(FOLLOW_LOWER_RATIO)));

        parameter.setFollowCallbackRatio(DealUtil.getDouble(trade.get(FOLLOW_CALLBACK_RATIO)));
        parameter.setFirstOrderPrice(DealUtil.getDouble(trade.get(FIRST_ORDER_PRICE)));
        parameter.setTargetProfitPrice(DealUtil.getDouble(trade.get(TARGET_PROFIT_PRICE)));

        //是否开启追踪止盈
        Object o = trade.get(IS_STOP_PROFIT_TRACE);
        Integer temp = 1;
        if ( o != null && StringUtils.isNotBlank(o.toString())) {
            temp = DealUtil.getInteger(o.toString());
        }
        parameter.setIsStopProfitTrace(temp);
        parameter.setTurnDownRatio(DealUtil.getDouble(trade.get(TURN_DOWN_RATIO)));
        parameter.setEmitRatio(DealUtil.getDouble(trade.get(EMIT_RATIO)));

        return parameter;
    }


}
