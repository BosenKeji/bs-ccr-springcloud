package cn.bosenkeji.vo.reason;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.sql.Timestamp;

public class Reason implements Serializable {
    private int id;

    private String name;

    private int status;

    private int reasonTypeId;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp createdAt;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp updatedAt;

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

    public int getReasonTypeId() {
        return reasonTypeId;
    }

    public void setReasonTypeId(int reasonTypeId) {
        this.reasonTypeId = reasonTypeId;
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