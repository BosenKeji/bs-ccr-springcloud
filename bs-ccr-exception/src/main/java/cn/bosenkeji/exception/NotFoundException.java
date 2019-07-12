package cn.bosenkeji.exception;

import cn.bosenkeji.enums.IProviderEnum;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @ClassName NotFoundException
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
public class NotFoundException extends RuntimeException {


    private Integer code;

    public NotFoundException(IProviderEnum iProviderEnum){
        super( iProviderEnum.getMessage()+ "不存在");
        this.setCode(iProviderEnum.getCode());
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
