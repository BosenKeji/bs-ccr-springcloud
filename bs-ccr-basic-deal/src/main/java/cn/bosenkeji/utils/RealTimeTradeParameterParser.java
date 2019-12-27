package cn.bosenkeji.utils;

import cn.bosenkeji.enums.DealEnum;
import cn.bosenkeji.vo.RealTimeTradeParameter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 *
 * mq取的实时信息解析
 * @author hjh
 *
 */

public class RealTimeTradeParameterParser {

    private JSONObject jsonObject;

    public RealTimeTradeParameterParser() { }

    public RealTimeTradeParameterParser(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public RealTimeTradeParameter getRealTimeTradeParameter() {
        RealTimeTradeParameter parameter = new RealTimeTradeParameter();

        String platFormName = DealUtil.getString(jsonObject.get(DealEnum.PLATFORM_NAME));
        String symbol = DealUtil.getString(jsonObject.get(DealEnum.SYMBOL));
        Double sellPrice = DealUtil.getDouble(((JSONObject)jsonObject.get("price")).get(DealEnum.SELL_PRICE));

        //买卖的深度
        JSONObject deep = (JSONObject)jsonObject.get("deep");
        JSONArray buyDeep = transformDeep(deep.get(DealEnum.BUY_DEEP));
        JSONArray sellDeep = transformDeep(deep.get(DealEnum.SELL_DEEP));

        parameter.setSellPrice(sellPrice);
        parameter.setBuyDeep(buyDeep);
        parameter.setSellDeep(sellDeep);
        parameter.setSymbol(symbol);
        parameter.setPlatFormName(platFormName);



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
