package cn.bosenkeji.vo;

/**
 * @ClassName RocketMQResult
 * @Description 确定买卖的对象，返回给node端
 * @Author hjh
 * @Date 2019-07-30 10:10
 * @Version 1.0
 */

public class RocketMQResult {

    private String accessKey;
    private String secretKey;
    private String symbol;
    private String type;

    public RocketMQResult() { }

    public RocketMQResult(String accessKey, String secretKey, String symbol, String type) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.symbol = symbol;
        this.type = type;
    }

    @Override
    public String toString() {
        return "RocketMQResult{" +
                "accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", symbol='" + symbol + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
