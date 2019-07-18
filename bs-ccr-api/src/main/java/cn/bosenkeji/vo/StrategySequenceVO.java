package cn.bosenkeji.vo;

public class StrategySequenceVO {

    private int id;
    private String name;
    private String tip;

    private int status;
    private String value;
    private int sortNum;

    public StrategySequenceVO() { }

    public StrategySequenceVO(int id, String name, String tip, int status, String value, int sortNum) {
        this.id = id;
        this.name = name;
        this.tip = tip;
        this.status = status;
        this.value = value;
        this.sortNum = sortNum;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getSortNum(Integer sortNum) {
        return this.sortNum;
    }

    public void setSortNum(int sortNum) {
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
