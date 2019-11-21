package cn.bosenkeji.vo.coin;

/**
 * @author CAJR
 * @date 2019/11/21 10:48 上午
 */
public class CoinPairCoinResult {

    private String valuationCoinName;

    private String baseCoinName;

    private String coinPairName;

    public String getValuationCoinName() {
        return valuationCoinName;
    }

    public void setValuationCoinName(String valuationCoinName) {
        this.valuationCoinName = valuationCoinName;
    }

    public String getBaseCoinName() {
        return baseCoinName;
    }

    public void setBaseCoinName(String baseCoinName) {
        this.baseCoinName = baseCoinName;
    }

    public String getCoinPairName() {
        return coinPairName;
    }

    public void setCoinPairName(String coinPairName) {
        this.coinPairName = coinPairName;
    }
}
