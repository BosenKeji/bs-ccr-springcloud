package cn.bosenkeji.vo;

import com.alibaba.fastjson.JSONArray;

import java.math.BigDecimal;
import java.util.Map;

/**
 * mq实时传过来的参数
 * @author hjh
 *
 */

public class RealTimeTradeParameter {

    private Double price;
    private JSONArray deep;
    private String symbol;

    public RealTimeTradeParameter() { }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public JSONArray getDeep() {
        return deep;
    }

    public void setDeep(JSONArray deep) {
        this.deep = deep;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
