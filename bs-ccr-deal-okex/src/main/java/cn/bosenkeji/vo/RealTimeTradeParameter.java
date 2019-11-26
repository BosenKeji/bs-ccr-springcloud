package cn.bosenkeji.vo;

import com.alibaba.fastjson.JSONArray;

/**
 * mq实时传过来的参数
 * @author hjh
 *
 */

public class RealTimeTradeParameter {

    private Double sellPrice;
    private JSONArray buyDeep;
    private JSONArray sellDeep;
    private String symbol;
    private String platFormName;
    private String setKey;

    public RealTimeTradeParameter() { }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public JSONArray getBuyDeep() {
        return buyDeep;
    }

    public void setBuyDeep(JSONArray buyDeep) {
        this.buyDeep = buyDeep;
    }

    public JSONArray getSellDeep() {
        return sellDeep;
    }

    public void setSellDeep(JSONArray sellDeep) {
        this.sellDeep = sellDeep;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPlatFormName() {
        return platFormName;
    }

    public void setPlatFormName(String platFormName) {
        this.platFormName = platFormName;
    }

    public String getSetKey() {
        return setKey;
    }

    public void setSetKey(String setKey) {
        this.setKey = setKey;
    }
}
