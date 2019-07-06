package cn.bosenkeji.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Coin implements Serializable{
    private int id;
    private String name;
    private int status;
    private Timestamp created_at;
    private Timestamp updated_at;

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

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdate_at() {
        return updated_at;
    }

    public void setUpdate_at(Timestamp update_at) {
        this.updated_at = update_at;
    }

    @Override
    public String toString() {
        return "Coin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", created_at=" + created_at +
                ", update_at=" + updated_at +
                '}';
    }
}
