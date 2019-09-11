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

        //price 可能出现double、BigDecimal、Double
        Double price = 0.0;
        Object priceObject = jsonObject.get(PRICE);

        if (priceObject != null) {
            if (priceObject instanceof Double) price = (Double) priceObject;
            if (priceObject instanceof Integer) price = Double.valueOf(priceObject.toString());
            if (priceObject instanceof BigDecimal) price = ((BigDecimal) priceObject).doubleValue();
        } else {
            return parameter;
        }
        Object o = jsonObject.get(DEEP);
        JSONArray jsonArray;
        if (o == null) {
            jsonArray = new JSONArray();
        } else {
            jsonArray = (JSONArray) o;
        }
        String symbol = DealUtil.getString(jsonObject.get(SYMBOL));

        parameter.setPrice(price);
        parameter.setDeep(jsonArray);
        parameter.setSymbol(symbol);

        return parameter;
    }
}
