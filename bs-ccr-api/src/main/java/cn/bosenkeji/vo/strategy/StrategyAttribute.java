package cn.bosenkeji.vo.strategy;

import java.sql.Timestamp;

public class StrategyAttribute {
    private int id;

    private String name;

    private int strategyId;

    private int isDefault;

    private int isTip;

    private int lever;

    private int strategySequenceId;

    private int rate;

    private double stopProfitRatio;

    private int isStopProfitTrace;

    private double stopProfitTraceTriggerRate;

    private double stopProfitTraceDropRate;

    private int isStopProfitMoney;

    private int isStopProfitGrid;

    private int buildReference;

    private int status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public StrategyAttribute(int id, String name, int strategyId, int isDefault, int isTip, int lever, int strategySequenceId, int rate, double stopProfitRatio, int isStopProfitTrace, double stopProfitTraceTriggerRate, double stopProfitTraceDropRate, int isStopProfitMoney, int isStopProfitGrid, int buildReference, int status, Timestamp createdAt, Timestamp updatedAt) {
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
        this.name = name == null ? null : name.trim();
    }

    public int getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(int strategyId) {
        this.strategyId = strategyId;
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

    public double getStopProfitRatio() {
        return stopProfitRatio;
    }

    public void setStopProfitRatio(double stopProfitRatio) {
        this.stopProfitRatio = stopProfitRatio;
    }

    public int getIsStopProfitTrace() {
        return isStopProfitTrace;
    }

    public void setIsStopProfitTrace(int isStopProfitTrace) {
        this.isStopProfitTrace = isStopProfitTrace;
    }

    public double getStopProfitTraceTriggerRate() {
        return stopProfitTraceTriggerRate;
    }

    public void setStopProfitTraceTriggerRate(double stopProfitTraceTriggerRate) {
        this.stopProfitTraceTriggerRate = stopProfitTraceTriggerRate;
    }

    public double getStopProfitTraceDropRate() {
        return stopProfitTraceDropRate;
    }

    public void setStopProfitTraceDropRate(double stopProfitTraceDropRate) {
        this.stopProfitTraceDropRate = stopProfitTraceDropRate;
    }

    public int getIsStopProfitMoney() {
        return isStopProfitMoney;
    }

    public void setIsStopProfitMoney(int isStopProfitMoney) {
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}