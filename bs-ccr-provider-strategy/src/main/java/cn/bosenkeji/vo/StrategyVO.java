package cn.bosenkeji.vo;

public class StrategyVO {

    private Integer id;
    private String name;
    private Byte status;

    private Integer lever;
    private Integer rate;
    private Byte buildReference;

    public StrategyVO(Integer id, String name, Byte status, Integer lever, Integer rate, Byte buildReference) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.lever = lever;
        this.rate = rate;
        this.buildReference = buildReference;
    }

    public StrategyVO() { }

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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Integer getLever() {
        return lever;
    }

    public void setLever(Integer lever) {
        this.lever = lever;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Byte getBuildReference() {
        return buildReference;
    }

    public void setBuildReference(Byte buildReference) {
        this.buildReference = buildReference;
    }


    @Override
    public String toString() {
        return "StrategyVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", lever=" + lever +
                ", rate=" + rate +
                ", buildReference=" + buildReference +
                '}';
    }
}
