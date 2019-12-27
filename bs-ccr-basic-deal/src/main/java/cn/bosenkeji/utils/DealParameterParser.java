package cn.bosenkeji.utils;

import cn.bosenkeji.enums.DealEnum;
import cn.bosenkeji.vo.DealParameter;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

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

    private Map trade;

    public DealParameterParser(Map trade) {
        this.trade = trade;
    }

    /**
     *
     * 获取redis在交易中的参数
     *
     **/

    public DealParameter getDealParameter() {
        DealParameter parameter = new DealParameter();
        parameter.setTrade(trade);

        parameter.setSymbol(DealUtil.getString(trade.get(DealEnum.SYMBOL)));
        parameter.setSignId(DealUtil.getString(trade.get(DealEnum.SIGN_ID)));

        parameter.setTradeStatus(DealUtil.getInteger(trade.get(DealEnum.TRADE_STATUS)));
        parameter.setPositionCost(DealUtil.getDouble(trade.get(DealEnum.POSITION_COST)));
        parameter.setPositionNum(DealUtil.getDouble(trade.get(DealEnum.POSITION_NUM)));

        parameter.setFinishedOrder(DealUtil.getInteger(trade.get(DealEnum.FINISHED_ORDER)));
        parameter.setMaxTradeOrder(DealUtil.getInteger(trade.get(DealEnum.MAX_TRADE_ORDER)));
        parameter.setStoreSplit(DealUtil.getDouble(trade.get(DealEnum.STORE_SPLIT)));

        // Map 特殊处理
        Object s = trade.get(DealEnum.BUY_VOLUME);
        if (s != null) {
            String unescape = StringEscapeUtils.unescapeJava(s.toString());
            JSONObject jsonBuyVolume = JSONObject.parseObject(unescape);
            parameter.setBuyVolume(jsonBuyVolume);
        }

        parameter.setFollowLowerRatio(DealUtil.getDouble(trade.get(DealEnum.FOLLOW_LOWER_RATIO)));

        parameter.setFollowCallbackRatio(DealUtil.getDouble(trade.get(DealEnum.FOLLOW_CALLBACK_RATIO)));
        parameter.setFirstOrderPrice(DealUtil.getDouble(trade.get(DealEnum.FIRST_ORDER_PRICE)));
        parameter.setTargetProfitPrice(DealUtil.getDouble(trade.get(DealEnum.TARGET_PROFIT_PRICE)));

        //是否开启追踪止盈
        Object o = trade.get(DealEnum.IS_STOP_PROFIT_TRACE);
        Integer temp = 1;
        if ( o != null && StringUtils.isNotBlank(o.toString())) {
            temp = DealUtil.getInteger(o.toString());
        }
        parameter.setIsStopProfitTrace(temp);
        parameter.setTurnDownRatio(DealUtil.getDouble(trade.get(DealEnum.TURN_DOWN_RATIO)));
        parameter.setEmitRatio(DealUtil.getDouble(trade.get(DealEnum.EMIT_RATIO)));

        return parameter;
    }


}
