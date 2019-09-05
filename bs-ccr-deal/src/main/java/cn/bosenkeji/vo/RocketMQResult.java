package cn.bosenkeji.vo;

/**
 *
 *
 *
 * @author hjh
 *
 */

public class RocketMQResult {

    private String accessKey;
    private String secretKey;
    private String symbol;
    private String type;

    public RocketMQResult() { }

    @Override
    public String toString() {
        return "RocketMQResult{" +
                "accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", symbol='" + symbol + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getType() {
        return type;
    }
}
