package cn.bosenkeji.vo.transaction;

import java.io.Serializable;

public class SumTradeCostAggregateVo implements Serializable {

    private long count =0;
    private double totalTradeCost =0;
    private String value="0";

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getTotalTradeCost() {
        return totalTradeCost;
    }

    public void setTotalTradeCost(double totalTradeCost) {
        this.totalTradeCost = totalTradeCost;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
