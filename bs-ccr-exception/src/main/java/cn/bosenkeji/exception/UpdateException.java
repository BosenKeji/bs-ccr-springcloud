package cn.bosenkeji.exception;

import cn.bosenkeji.enums.IProviderEnum;

/**
 * @Author CAJR
 * @create 2019/7/24 10:34
 */
public class UpdateException extends RuntimeException {
    private Integer code;

    public UpdateException(IProviderEnum iProviderEnum){

        super( iProviderEnum.getMessage()+ "更新失败");
        this.setCode(iProviderEnum.getCode());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
