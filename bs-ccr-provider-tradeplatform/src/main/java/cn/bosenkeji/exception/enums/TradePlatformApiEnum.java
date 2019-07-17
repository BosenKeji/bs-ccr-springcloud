package cn.bosenkeji.exception.enums;

import cn.bosenkeji.enums.IProviderEnum;

/**
 * @Author CAJR
 * @create 2019/7/15 15:14
 */
public enum TradePlatformApiEnum implements IProviderEnum {
    NAME(10000, "交易平台Api");

    private Integer code;

    private String message;

    TradePlatformApiEnum(Integer code, String message) {
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
    }}
