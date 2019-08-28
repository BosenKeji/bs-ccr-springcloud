package cn.bosenkeji.vo;

import java.util.Map;

/**
 * mq实时传过来的参数
 * @author hjh
 *
 */

public class RealTimeTradeParameter {

    private Double price;
    private Map<Double, Double> deep;
    private String symbol;

    public RealTimeTradeParameter() { }

    public RealTimeTradeParameter(Double price, Map<Double, Double> deep, String symbol) {
        this.price = price;
        this.deep = deep;
        this.symbol = symbol;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Map<Double, Double> getDeep() {
        return deep;
    }

    public void setDeep(Map<Double, Double> deep) {
        this.deep = deep;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
