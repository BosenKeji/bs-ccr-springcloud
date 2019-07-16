package cn.bosenkeji.exception.enums;

import cn.bosenkeji.enums.IProviderEnum;

public enum StrategySequenceEnum implements IProviderEnum {

    ;

    private Integer code;

    private String message;

    StrategySequenceEnum(Integer code, String message) {
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
