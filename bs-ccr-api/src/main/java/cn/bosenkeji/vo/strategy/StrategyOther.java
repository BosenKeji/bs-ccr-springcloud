package cn.bosenkeji.vo.strategy;

public class StrategyOther {

    private int id;
    private String name;
    private int status;

    private int isDefault;
    private int isTip;
    private int lever;

    private int strategySequenceId;
    private int rate;
    private int stopProfitRatio;

    private int isStopProfitTrace;
    private int stopProfitTraceTriggerRate;
    private int stopProfitTraceDropRate;

    private double isStopProfitMoney;
    private int isStopProfitGrid;
    private int buildReference;

    public StrategyOther() { }

    public StrategyOther(int id, String name, int status, int isDefault, int isTip, int lever, int strategySequenceId, int rate, int stopProfitRatio, int isStopProfitTrace, int stopProfitTraceTriggerRate, int stopProfitTraceDropRate, double isStopProfitMoney, int isStopProfitGrid, int buildReference) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.isDefault = isDefault;
        this.isTip = isTip;
        this.lever = lever;
        this.strategySequenceId = strategySequenceId;
        this.rate = rate;
        this.stopProfitRatio = stopProfitRatio;
        this.isStopProfitTrace = isStopProfitTrace;
        this.stopProfitTraceTriggerRate = stopProfitTraceTriggerRate;
        this.stopProfitTraceDropRate = stopProfitTraceDropRate;
        this.isStopProfitMoney = isStopProfitMoney;
        this.isStopProfitGrid = isStopProfitGrid;
        this.buildReference = buildReference;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public int getIsTip() {
        return isTip;
    }

    public void setIsTip(int isTip) {
        this.isTip = isTip;
    }

    public int getLever() {
        return lever;
    }

    public void setLever(int lever) {
        this.lever = lever;
    }

    public int getStrategySequenceId() {
        return strategySequenceId;
    }

    public void setStrategySequenceId(int strategySequenceId) {
        this.strategySequenceId = strategySequenceId;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getStopProfitRatio() {
        return stopProfitRatio;
    }

    public void setStopProfitRatio(int stopProfitRatio) {
        this.stopProfitRatio = stopProfitRatio;
    }

    public int getIsStopProfitTrace() {
        return isStopProfitTrace;
    }

    public void setIsStopProfitTrace(int isStopProfitTrace) {
        this.isStopProfitTrace = isStopProfitTrace;
    }

    public int getStopProfitTraceTriggerRate() {
        return stopProfitTraceTriggerRate;
    }

    public void setStopProfitTraceTriggerRate(int stopProfitTraceTriggerRate) {
        this.stopProfitTraceTriggerRate = stopProfitTraceTriggerRate;
    }

    public int getStopProfitTraceDropRate() {
        return stopProfitTraceDropRate;
    }

    public void setStopProfitTraceDropRate(int stopProfitTraceDropRate) {
        this.stopProfitTraceDropRate = stopProfitTraceDropRate;
    }

    public double getIsStopProfitMoney() {
        return isStopProfitMoney;
    }

    public void setIsStopProfitMoney(double isStopProfitMoney) {
        this.isStopProfitMoney = isStopProfitMoney;
    }

    public int getIsStopProfitGrid() {
        return isStopProfitGrid;
    }

    public void setIsStopProfitGrid(int isStopProfitGrid) {
        this.isStopProfitGrid = isStopProfitGrid;
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
                ", isDefault=" + isDefault +
                ", isTip=" + isTip +
                ", lever=" + lever +
                ", strategySequenceId=" + strategySequenceId +
                ", rate=" + rate +
                ", stopProfitRatio=" + stopProfitRatio +
                ", isStopProfitTrace=" + isStopProfitTrace +
                ", stopProfitTraceTriggerRate=" + stopProfitTraceTriggerRate +
                ", stopProfitTraceDropRate=" + stopProfitTraceDropRate +
                ", isStopProfitMoney=" + isStopProfitMoney +
                ", isStopProfitGrid=" + isStopProfitGrid +
                ", buildReference=" + buildReference +
                '}';
    }
}
