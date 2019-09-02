package cn.bosenkeji.utils;

import cn.bosenkeji.messaging.MySource;
import cn.bosenkeji.vo.DealParameter;
import cn.bosenkeji.vo.RocketMQResult;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * deal模块通用的方法
 *
 * @author hjh
 */

public class DealUtil {

    public static final String TRADE_KEYS_PATTERN = "trade-condition_*";

    public static final String TRADE_TYPE_BUY = "buy";

    public static final String TRADE_TYPE_SELL = "sell";


    /**
     *
     * 将一个JSONArray对象转换为一个Map
     *
     **/
    static Map<Double, Double> convertJsonArrayToMap(JSONArray jsonArray) {
        Map<Double, Double> map = new HashMap<>();
        for (Object obj : jsonArray){
            JSONArray o = (JSONArray) obj;
            map.put(Double.valueOf(o.get(0).toString()),Double.valueOf(o.get(1).toString()));
        }
        return map;
    }


    static String getString(Object o) {
        return o == null ? "" : o.toString();
    }

    static Integer getInteger(Object o) {
        Integer temp = 0;
        if (o != null) {
            temp = Integer.valueOf(o instanceof Integer ? o.toString() : "0");
        }
        return temp;
    }

    static Double getDouble(Object o) {
        Double temp = 0.0;
        temp = o == null ? Double.valueOf("0.0") : Double.valueOf(o.toString());
        return temp;
    }

    public static boolean sendMessage(DealParameter dealParameter, String type, MySource source) {
        RocketMQResult rocketMQResult = new RocketMQResult();

        String accessKey = dealParameter.getAccessKey();
        String secretKey = dealParameter.getSecretKey();
        String symbol = dealParameter.getSymbol();

        rocketMQResult.setAccessKey(accessKey);
        rocketMQResult.setSecretKey(secretKey);
        rocketMQResult.setSymbol(symbol);
        rocketMQResult.setType(type);

        JSONObject jsonResult = (JSONObject) JSONObject.toJSON(rocketMQResult);
        Message<String> jsonMessage = MessageBuilder.withPayload(jsonResult.toJSONString()).build();

        return source.output1().send(jsonMessage);
    }
}
