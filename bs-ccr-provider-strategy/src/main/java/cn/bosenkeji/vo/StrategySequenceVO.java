package cn.bosenkeji.vo;

public class StrategySequenceVO {

    private Integer id;
    private String name;
    private String tip;

    private Byte status;
    private String value;
    private Integer sortNum;

    public StrategySequenceVO() { }

    public StrategySequenceVO(Integer id, String name, String tip, Byte status, String value, Integer sortNum) {
        this.id = id;
        this.name = name;
        this.tip = tip;
        this.status = status;
        this.value = value;
        this.sortNum = sortNum;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSortNum(Integer sortNum) {
        return this.sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public String toString() {
        return "StrategySequenceVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tip='" + tip + '\'' +
                ", status=" + status +
                ", value='" + value + '\'' +
                ", sortNum=" + sortNum +
                '}';
    }
}
