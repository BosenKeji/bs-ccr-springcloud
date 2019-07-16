package cn.bosenkeji.exception.enums;

import cn.bosenkeji.enums.IProviderEnum;

/**
 * @Author CAJR
 * @create 2019/7/16 15:10
 */
public enum TradePlatformCoinPairEnum implements IProviderEnum {
    NAME(10000, "平台货币对");

    private Integer code;

    private String message;

    TradePlatformCoinPairEnum(Integer code, String message) {
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
