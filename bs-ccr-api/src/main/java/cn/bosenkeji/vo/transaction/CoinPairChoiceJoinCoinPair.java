package cn.bosenkeji.vo.transaction;


public class CoinPairChoiceJoinCoinPair {

    private int id;

    private int userId;

    private int isStart;

    private int status;

    private int coinPartnerId;

    private String coinPairName;

    public CoinPairChoiceJoinCoinPair() { }


    public CoinPairChoiceJoinCoinPair(int id, int userId, int isStart, int status, int coinPartnerId, String coinPairName) {
        this.id = id;
        this.userId = userId;
        this.isStart = isStart;
        this.status = status;
        this.coinPartnerId = coinPartnerId;
        this.coinPairName = coinPairName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIsStart() {
        return isStart;
    }

    public void setIsStart(int isStart) {
        this.isStart = isStart;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCoinPartnerId() {
        return coinPartnerId;
    }

    public void setCoinPartnerId(int coinPartnerId) {
        this.coinPartnerId = coinPartnerId;
    }

    public String getCoinPairName() {
        return coinPairName;
    }

    public void setCoinPairName(String coinPairName) {
        this.coinPairName = coinPairName;
    }

    @Override
    public String toString() {
        return "CoinPairChoiceJoinCoinPair{" +
                "id=" + id +
                ", userId=" + userId +
                ", isStart=" + isStart +
                ", status=" + status +
                ", coinPartnerId=" + coinPartnerId +
                ", coinPairName='" + coinPairName + '\'' +
                '}';
    }
}
