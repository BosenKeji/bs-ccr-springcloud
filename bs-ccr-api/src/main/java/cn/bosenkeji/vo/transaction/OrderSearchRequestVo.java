package cn.bosenkeji.vo.transaction;

import java.sql.Timestamp;

public class OrderSearchRequestVo {

    private Integer time;
    private Timestamp beginTime;
    private Timestamp endTime;

    private String idsStr;
    private Integer tradeType;

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getIdsStr() {
        return idsStr;
    }

    public void setIdsStr(String idsStr) {
        this.idsStr = idsStr;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }
}
