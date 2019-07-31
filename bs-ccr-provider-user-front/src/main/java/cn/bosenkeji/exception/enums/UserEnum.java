package cn.bosenkeji.exception.enums;

import cn.bosenkeji.enums.IProviderEnum;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.exception.enums
 * @Version V1.0
 * @create 2019-07-31 17:48
 */
public enum UserEnum implements IProviderEnum {
    NAME(10000, "用户");

    private Integer code;

    private String message;

    UserEnum(Integer code, String message) {
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
