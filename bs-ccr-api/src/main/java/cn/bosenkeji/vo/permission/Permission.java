package cn.bosenkeji.vo.permission;

import java.sql.Timestamp;

public class Permission {
    private int id;

    private String name;

    private String url;

    private int httpMethod;

    private int type;

    private String description;

    private int status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public Permission(int id, String name, String url, int httpMethod) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.httpMethod = httpMethod;
    }

    public Permission(int id, String name, String url, int httpMethod, int type, String description, int status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.httpMethod = httpMethod;
        this.type = type;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Permission() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public int getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(int httpMethod) {
        this.httpMethod = httpMethod;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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