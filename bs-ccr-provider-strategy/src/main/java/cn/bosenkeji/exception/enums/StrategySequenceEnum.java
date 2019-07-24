package cn.bosenkeji.exception.enums;

import cn.bosenkeji.enums.IProviderEnum;

public enum StrategySequenceEnum implements IProviderEnum {

    STRATEGY_SEQUENCE_EXIST(400,"策略数列已存在"),
    STRATEGY_SEQUENCE_NOT_FOUND(400,"未找到相关的策略数列")
    ;

    private Integer code;

    private String message;

    StrategySequenceEnum() { }

    StrategySequenceEnum(Integer code, String message) {
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
