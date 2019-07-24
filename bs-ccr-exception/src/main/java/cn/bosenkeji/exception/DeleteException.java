package cn.bosenkeji.exception;

import cn.bosenkeji.enums.IProviderEnum;

/**
 * @Author CAJR
 * @create 2019/7/24 10:35
 */
public class DeleteException extends RuntimeException {
    private Integer code;

    public DeleteException(IProviderEnum iProviderEnum){

        super( iProviderEnum.getMessage()+ "删除失败");
        this.setCode(iProviderEnum.getCode());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
