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

    private static final String PRICE = "price";
    private static final String DEEP = "deep";
    private static final String SYMBOL = "symbol";

    public RealTimeTradeParameterParser() { }

    public RealTimeTradeParameterParser(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public RealTimeTradeParameter getRealTimeTradeParameter() {
        RealTimeTradeParameter parameter = new RealTimeTradeParameter();
        Double price = ((BigDecimal)jsonObject.get(PRICE)).doubleValue();
        Object o = jsonObject.get(DEEP);
        JSONArray jsonArray;
        if (o == null) {
            jsonArray = new JSONArray();
        } else {
            jsonArray = (JSONArray) o;
        }
        Map<Double, Double> deep = DealUtil.convertJsonArrayToMap(jsonArray);

        String symbol = DealUtil.getString(jsonObject.get(SYMBOL));

        parameter.setPrice(price);
        parameter.setDeep(deep);
        parameter.setSymbol(symbol);

        return parameter;
    }
}
