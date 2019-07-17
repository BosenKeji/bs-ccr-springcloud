package cn.bosenkeji.exception.enums;

import cn.bosenkeji.enums.IProviderEnum;

/**
 * @author xivin
 * @create 2019-07-12 15:03
 */
public enum UserProductComboEnum implements IProviderEnum {
    NAME(10000,"用户产品套餐");

    private Integer code;
    private String message;

    UserProductComboEnum(Integer code, String message) {
        this.code=code;
        this.message=message;
    }
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
