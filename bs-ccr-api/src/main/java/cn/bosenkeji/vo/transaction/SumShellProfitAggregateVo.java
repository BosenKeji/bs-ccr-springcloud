package cn.bosenkeji.vo.transaction;

import java.io.Serializable;

public class SumShellProfitAggregateVo implements Serializable {

    private String count;
    private String totalShellProfit;
    private String value;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTotalShellProfit() {
        return totalShellProfit;
    }

    public void setTotalShellProfit(String totalShellProfit) {
        this.totalShellProfit = totalShellProfit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
