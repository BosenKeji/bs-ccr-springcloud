package cn.bosenkeji.vo.strategy;


import java.sql.Timestamp;

public class StrategySequenceValue {
    private int id;

    private int strategySequenceId;

    private String value;

    private int sortNum;

    private int status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public StrategySequenceValue(int id, int strategySequenceId, String value, int sortNum, int status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.strategySequenceId = strategySequenceId;
        this.value = value;
        this.sortNum = sortNum;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public StrategySequenceValue() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStrategySequenceId() {
        return strategySequenceId;
    }

    public void setStrategySequenceId(int strategySequenceId) {
        this.strategySequenceId = strategySequenceId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
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