package cn.bosenkeji.vo.permission;

import java.sql.Timestamp;

public class PermissionGroupPermission {
    private int id;

    private int permissionGroupId;

    private int permissionId;

    private int status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public PermissionGroupPermission(int id, int permissionGroupId, int permissionId, int status, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.permissionGroupId = permissionGroupId;
        this.permissionId = permissionId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PermissionGroupPermission() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPermissionGroupId() {
        return permissionGroupId;
    }

    public void setPermissionGroupId(int permissionGroupId) {
        this.permissionGroupId = permissionGroupId;
    }

    public int getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
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