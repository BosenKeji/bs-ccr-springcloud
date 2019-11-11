package cn.bosenkeji.vo.transaction;

import java.io.Serializable;

public class SumTradeCostAggregateVo implements Serializable {

    private String count;
    private String totalTradeCost;
    private String value;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotalTradeCost() {
        return totalTradeCost;
    }

    public void setTotalTradeCost(String totalTradeCost) {
        this.totalTradeCost = totalTradeCost;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
