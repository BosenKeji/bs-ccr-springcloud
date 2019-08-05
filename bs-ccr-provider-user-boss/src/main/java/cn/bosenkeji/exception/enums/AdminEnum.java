package cn.bosenkeji.exception.enums;

import cn.bosenkeji.enums.IProviderEnum;

public enum  AdminEnum implements IProviderEnum {

    ADMIN_EXIST(400,"管理员已存在"),
    ADMIN_NOT_FOUND(400,"管理员不存在"),

    NAME(10000, "管理员");

    private Integer code;

    private String message;

    AdminEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}