package cn.bosenkeji.vo;

import java.util.Date;

public class StrategyAttribute {
    private Integer id;

    private String name;

    private Integer strategyId;

    private Byte isDefault;

    private Byte isTip;

    private Integer lever;

    private Integer strategySequenceId;

    private Integer rate;

    private Byte stopProfitRatio;

    private Byte isStopProfitTrace;

    private Byte stopProfitTraceTriggerRate;

    private Byte stopProfitTraceDropRate;

    private Byte isStopProfitMoney;

    private Byte isStopProfitGrid;

    private Byte buildReference;

    private Byte status;

    private Date createdAt;

    private Date updatedAt;

    public StrategyAttribute(Integer id, String name, Integer strategyId, Byte isDefault, Byte isTip, Integer lever, Integer strategySequenceId, Integer rate, Byte stopProfitRatio, Byte isStopProfitTrace, Byte stopProfitTraceTriggerRate, Byte stopProfitTraceDropRate, Byte isStopProfitMoney, Byte isStopProfitGrid, Byte buildReference, Byte status, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.strategyId = strategyId;
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
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public StrategyAttribute() {
        super();
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
        this.name = name == null ? null : name.trim();
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public Byte getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Byte isDefault) {
        this.isDefault = isDefault;
    }

    public Byte getIsTip() {
        return isTip;
    }

    public void setIsTip(Byte isTip) {
        this.isTip = isTip;
    }

    public Integer getLever() {
        return lever;
    }

    public void setLever(Integer lever) {
        this.lever = lever;
    }

    public Integer getStrategySequenceId() {
        return strategySequenceId;
    }

    public void setStrategySequenceId(Integer strategySequenceId) {
        this.strategySequenceId = strategySequenceId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Byte getStopProfitRatio() {
        return stopProfitRatio;
    }

    public void setStopProfitRatio(Byte stopProfitRatio) {
        this.stopProfitRatio = stopProfitRatio;
    }

    public Byte getIsStopProfitTrace() {
        return isStopProfitTrace;
    }

    public void setIsStopProfitTrace(Byte isStopProfitTrace) {
        this.isStopProfitTrace = isStopProfitTrace;
    }

    public Byte getStopProfitTraceTriggerRate() {
        return stopProfitTraceTriggerRate;
    }

    public void setStopProfitTraceTriggerRate(Byte stopProfitTraceTriggerRate) {
        this.stopProfitTraceTriggerRate = stopProfitTraceTriggerRate;
    }

    public Byte getStopProfitTraceDropRate() {
        return stopProfitTraceDropRate;
    }

    public void setStopProfitTraceDropRate(Byte stopProfitTraceDropRate) {
        this.stopProfitTraceDropRate = stopProfitTraceDropRate;
    }

    public Byte getIsStopProfitMoney() {
        return isStopProfitMoney;
    }

    public void setIsStopProfitMoney(Byte isStopProfitMoney) {
        this.isStopProfitMoney = isStopProfitMoney;
    }

    public Byte getIsStopProfitGrid() {
        return isStopProfitGrid;
    }

    public void setIsStopProfitGrid(Byte isStopProfitGrid) {
        this.isStopProfitGrid = isStopProfitGrid;
    }

    public Byte getBuildReference() {
        return buildReference;
    }

    public void setBuildReference(Byte buildReference) {
        this.buildReference = buildReference;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}