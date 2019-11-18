package cn.bosenkeji.vo.transaction;

import java.util.List;

/**
 * @author CAJR
 * @date 2019/11/18 10:08 上午
 */
public class CoinPairChoicePositionDetailResult {

    private String coinPairChoiceName;

    private List<TradeOrder> tradeOrders;

    public String getCoinPairChoiceName() {
        return coinPairChoiceName;
    }

    public void setCoinPairChoiceName(String coinPairChoiceName) {
        this.coinPairChoiceName = coinPairChoiceName;
    }

    public List<TradeOrder> getTradeOrders() {
        return tradeOrders;
    }

    public void setTradeOrders(List<TradeOrder> tradeOrders) {
        this.tradeOrders = tradeOrders;
    }
}
