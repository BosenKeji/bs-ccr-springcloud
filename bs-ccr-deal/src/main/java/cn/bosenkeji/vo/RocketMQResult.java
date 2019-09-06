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
    private Integer finished_order;

    public RocketMQResult() { }

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

    public Integer getFinished_order() {
        return finished_order;
    }

    public void setFinished_order(Integer finished_order) {
        this.finished_order = finished_order;
    }
}
