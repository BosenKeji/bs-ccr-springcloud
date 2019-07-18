package cn.bosenkeji.exception.enums;

import cn.bosenkeji.enums.IProviderEnum;

/**
 * @Author CAJR
 * @create 2019/7/16 15:12
 */
public enum TradePlatformEnum implements IProviderEnum {
    NAME(10000, "交易平台");

    private Integer code;

    private String message;

    TradePlatformEnum(Integer code, String message) {
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
