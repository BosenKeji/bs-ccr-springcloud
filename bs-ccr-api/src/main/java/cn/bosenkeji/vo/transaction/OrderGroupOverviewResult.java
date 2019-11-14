package cn.bosenkeji.vo.transaction;

/**
 * @author CAJR
 * @date 2019/11/13 7:29 下午
 */
public class OrderGroupOverviewResult {

    private String coinPairName;

    private int endNumbers;

    private double totalProfit;

    private double trackProfit;

    public String getCoinPairName() {
        return coinPairName;
    }

    public void setCoinPairName(String coinPairName) {
        this.coinPairName = coinPairName;
    }

    public int getEndNumbers() {
        return endNumbers;
    }

    public void setEndNumbers(int endNumbers) {
        this.endNumbers = endNumbers;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public double getTrackProfit() {
        return trackProfit;
    }

    public void setTrackProfit(double trackProfit) {
        this.trackProfit = trackProfit;
    }
}
