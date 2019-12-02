package cn.bosenkeji.vo;

/**
 *
 *
 *
 * @author hjh
 *
 */

public class RocketMQResult {

    private String symbol;
    private String signId;
    private String type;
    private Integer finished_order;
    private String plantFormName;

    public RocketMQResult() { }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getPlantFormName() {
        return plantFormName;
    }

    public void setPlantFormName(String plantFormName) {
        this.plantFormName = plantFormName;
    }

    @Override
    public String toString() {
        return "RocketMQResult{" +
                "symbol='" + symbol + '\'' +
                ", signId='" + signId + '\'' +
                ", type='" + type + '\'' +
                ", finished_order=" + finished_order +
                ", plantFormName='" + plantFormName + '\'' +
                '}';
    }
}
