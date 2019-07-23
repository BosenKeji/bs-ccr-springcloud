package cn.bosenkeji.vo.strategy;

public class StrategyOther {

    private int id;
    private String name;
    private int status;

    private int lever;
    private int rate;
    private int buildReference;

    public StrategyOther(int id, String name, int status, int lever, int rate, int buildReference) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.lever = lever;
        this.rate = rate;
        this.buildReference = buildReference;
    }

    public StrategyOther() { }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLever() {
        return lever;
    }

    public void setLever(int lever) {
        this.lever = lever;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getBuildReference() {
        return buildReference;
    }

    public void setBuildReference(int buildReference) {
        this.buildReference = buildReference;
    }


    @Override
    public String toString() {
        return "StrategyOther{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", lever=" + lever +
                ", rate=" + rate +
                ", buildReference=" + buildReference +
                '}';
    }
}
