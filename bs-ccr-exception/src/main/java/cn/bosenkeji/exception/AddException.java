package cn.bosenkeji.exception;

import cn.bosenkeji.enums.IProviderEnum;

/**
 * @ClassName NotFoundException
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
public class AddException extends RuntimeException {


    private Integer code;

    public AddException(IProviderEnum iProviderEnum){

        super( iProviderEnum.getMessage()+ "插入失败");
        this.setCode(iProviderEnum.getCode());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
