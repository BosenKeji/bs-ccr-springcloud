package cn.bosenkeji.vo;

/**
 * @author CAJR
 * @date 2019/12/2 7:24 下午
 */
public class OrderGroupIdMQResult {
    private int orderGroupId;

    private int coinPairChoiceId;

    private String key;

    private String orderId;

    public OrderGroupIdMQResult( int coinPairChoiceId,int orderGroupId, String key,String orderId) {
        this.orderGroupId = orderGroupId;
        this.coinPairChoiceId = coinPairChoiceId;
        this.key = key;
        this.orderId = orderId;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
