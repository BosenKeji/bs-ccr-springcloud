package cn.bosenkeji.vo.transaction;

import java.io.Serializable;

public class SumShellProfitAggregateVo implements Serializable {

    private long count = 0;
    private double totalShellProfit = 0;
    private double totalTrackProfit = 0;
    private String value = "0";

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public double getTotalShellProfit() {
        return totalShellProfit;
    }

    public void setTotalShellProfit(double totalShellProfit) {
        this.totalShellProfit = totalShellProfit;
    }

    public double getTotalTrackProfit() {
        return totalTrackProfit;
    }

    public void setTotalTrackProfit(double totalTrackProfit) {
        this.totalTrackProfit = totalTrackProfit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
