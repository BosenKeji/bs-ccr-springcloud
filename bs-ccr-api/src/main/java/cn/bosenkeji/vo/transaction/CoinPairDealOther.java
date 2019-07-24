package cn.bosenkeji.vo.transaction;

public class CoinPairDealOther {

    private int id;

    private int coinPartnerChoicId;

    private int type;

    private int quantity;

    private int status;

    public CoinPairDealOther() { }

    public CoinPairDealOther(int id, int coinPartnerChoicId, int type, int quantity, int status) {
        this.id = id;
        this.coinPartnerChoicId = coinPartnerChoicId;
        this.type = type;
        this.quantity = quantity;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoinPartnerChoicId() {
        return coinPartnerChoicId;
    }

    public void setCoinPartnerChoicId(int coinPartnerChoicId) {
        this.coinPartnerChoicId = coinPartnerChoicId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CoinPairDealOther{" +
                "id=" + id +
                ", coinPartnerChoicId=" + coinPartnerChoicId +
                ", type=" + type +
                ", quantity=" + quantity +
                ", status=" + status +
                '}';
    }
}
