package cn.bosenkeji.vo.cdKey;

public class GenerateCdKeyParam {

    private Integer number;
    private Integer productComboId;
    private String prefix;
    private String remark;

    private Integer productId;
    private Integer time;

    public GenerateCdKeyParam() { }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getProductComboId() {
        return productComboId;
    }

    public void setProductComboId(Integer productComboId) {
        this.productComboId = productComboId;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
