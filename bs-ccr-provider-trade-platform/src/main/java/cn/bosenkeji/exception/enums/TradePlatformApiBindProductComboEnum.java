package cn.bosenkeji.exception.enums;

import cn.bosenkeji.enums.IProviderEnum;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.exception.enums
 * @Version V1.0
 * @create 2019-07-27 15:59
 */
public enum TradePlatformApiBindProductComboEnum implements IProviderEnum {
    NAME(10000,"交易平台api绑定用户套餐");

    private Integer code;

    private String message;

    TradePlatformApiBindProductComboEnum(Integer code, String message) {
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
