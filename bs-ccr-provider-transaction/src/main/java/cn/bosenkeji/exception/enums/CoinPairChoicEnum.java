package cn.bosenkeji.exception.enums;

import cn.bosenkeji.enums.IProviderEnum;

/**
 * @Author CAJR
 * @create 2019/7/17 16:43
 */
public enum CoinPairChoicEnum implements IProviderEnum {
    NAME(10000, "自选货币");

    private Integer code;

    private String message;

    CoinPairChoicEnum(Integer code, String message) {
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