package cn.bosenkeji.exception.enums;

import cn.bosenkeji.enums.IProviderEnum;

public enum  AdminEnum implements IProviderEnum {

    ADMIN_EXIST(400,"策略已存在"),
    ADMIN_NOT_FOUND(400,"未找到相关的策略");

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