package cn.bosenkeji.utils;

import cn.bosenkeji.vo.RealTimeTradeParameter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * mq取的实时信息解析
 * @author hjh
 *
 */

public class RealTimeTradeParameterParser {

    private JSONObject jsonObject;

    private static final String SELL_PRICE = "sell";
    private static final String BUY_DEEP = "bids";
    private static final String SELL_DEEP = "asks";
    private static final String SYMBOL = "symbol";

    public RealTimeTradeParameterParser() { }

    public RealTimeTradeParameterParser(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public RealTimeTradeParameter getRealTimeTradeParameter() {
        RealTimeTradeParameter parameter = new RealTimeTradeParameter();

        String symbol = DealUtil.getString(jsonObject.get(SYMBOL));
        Double sellPrice = DealUtil.getDouble(((JSONObject)jsonObject.get("price")).get(SELL_PRICE));

        JSONObject deep = (JSONObject)jsonObject.get("deep");
        JSONArray buyDeep = transformDeep(deep.get(BUY_DEEP));
        JSONArray sellDeep = transformDeep(deep.get(SELL_DEEP));

        parameter.setSellPrice(sellPrice);
        parameter.setBuyDeep(buyDeep);
        parameter.setSellDeep(sellDeep);
        parameter.setSymbol(symbol);

        return parameter;
    }

    private JSONArray transformDeep(Object o) {
        JSONArray deep;
        if (o == null) {
            deep = new JSONArray();
        } else {
            deep = (JSONArray) o;
        }
        return deep;
    }
}
