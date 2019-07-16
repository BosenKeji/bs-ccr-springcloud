package cn.bosenkeji.vo;

import java.util.Date;

public class StrategySequenceValue {
    private Integer id;

    private Integer strategySequenceId;

    private String value;

    private Integer sortNum;

    private Byte status;

    private Date createdAt;

    private Date updatedAt;

    public StrategySequenceValue(Integer id, Integer strategySequenceId, String value, Integer sortNum, Byte status, Date createdAt, Date updatedAt) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStrategySequenceId() {
        return strategySequenceId;
    }

    public void setStrategySequenceId(Integer strategySequenceId) {
        this.strategySequenceId = strategySequenceId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
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