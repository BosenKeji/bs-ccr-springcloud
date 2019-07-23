package cn.bosenkeji.exception.enums;

import cn.bosenkeji.enums.IProviderEnum;

/**
 * @Author CAJR
 * @create 2019/7/18 11:01
 */
public enum CoinPairChoicAttributeEnum implements IProviderEnum {
    NAME(10000, "该自选货币属性");

    private Integer code;

    private String message;

    CoinPairChoicAttributeEnum(Integer code, String message) {
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