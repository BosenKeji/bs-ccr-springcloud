package cn.bosenkeji.exception.enums;

import cn.bosenkeji.enums.IProviderEnum;

/**
 * @Author CAJR
 * @create 2019/7/18 19:29
 */
public enum CoinPairChoicAttributeCustomEnum implements IProviderEnum {
    NAME(10000, "自选货币自定义属性");

    private Integer code;

    private String message;

    CoinPairChoicAttributeCustomEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;}
}