package cn.bosenkeji.exception.enums;

import cn.bosenkeji.enums.IProviderEnum;

public enum CoinPairDealEnum implements IProviderEnum {

    COIN_PAIR_DEAL_EXIST(400,"货币对交易已存在");

    ;
    private Integer code;

    private String message;

    CoinPairDealEnum() { }

    CoinPairDealEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
