package cn.bosenkeji.exception.enums;

import cn.bosenkeji.enums.IProviderEnum;

/**
 * @Author CAJR
 * @create 2019/7/12 16:41
 */
public enum CoinSortEnum implements IProviderEnum {
    NAME(10000, "货币排序");

    private Integer code;

    private String message;

    CoinSortEnum(Integer code, String message) {
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
