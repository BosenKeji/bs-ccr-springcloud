package cn.bosenkeji.exception.enums;

import cn.bosenkeji.enums.IProviderEnum;

/**
 * @Author CAJR
 * @create 2019/7/12 16:03
 */
public enum CoinPairEnum implements IProviderEnum {

    NAME(10000,"货币对");

    private Integer code;

    private String message;

    CoinPairEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public Integer getCode() {
        return null;
    }
}
