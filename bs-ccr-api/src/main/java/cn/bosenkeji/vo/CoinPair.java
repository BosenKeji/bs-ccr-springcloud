package cn.bosenkeji.vo;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @Author CAJR
 * @create 2019/7/9 16:27
 */

public class CoinPair implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private int id;

    private String name;

    private  int isPopular;

    private int isOfficialSet;

    private int status;

    private Timestamp createdAt;

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

    public int getIsPopular() {
        return isPopular;
    }

    public void setIsPopular(int isPopular) {
        this.isPopular = isPopular;
    }

    public int getIsOfficialSet() {
        return isOfficialSet;
    }

    public void setIsOfficialSet(int isOfficialSet) {
        this.isOfficialSet = isOfficialSet;
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

    @Override
    public String toString() {
        return "CoinPair{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isPopular=" + isPopular +
                ", isOfficialSet=" + isOfficialSet +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}