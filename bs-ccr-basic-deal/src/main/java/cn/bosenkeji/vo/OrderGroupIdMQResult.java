package cn.bosenkeji.vo;

/**
 * @author CAJR
 * @date 2019/12/2 7:24 下午
 */
public class OrderGroupIdMQResult {
    private int orderGroupId;

    private int coinPairChoiceId;

    private String key;

    public OrderGroupIdMQResult( int coinPairChoiceId,int orderGroupId, String key) {
        this.orderGroupId = orderGroupId;
        this.coinPairChoiceId = coinPairChoiceId;
        this.key = key;
    }

    public int getOrderGroupId() {
        return orderGroupId;
    }

    public void setOrderGroupId(int orderGroupId) {
        this.orderGroupId = orderGroupId;
    }

    public int getCoinPairChoiceId() {
        return coinPairChoiceId;
    }

    public void setCoinPairChoiceId(int coinPairChoiceId) {
        this.coinPairChoiceId = coinPairChoiceId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "OrderGroupIdMQResult{" +
                "orderGroupId=" + orderGroupId +
                ", coinPairChoiceId=" + coinPairChoiceId +
                ", key='" + key + '\'' +
                '}';
    }
}
