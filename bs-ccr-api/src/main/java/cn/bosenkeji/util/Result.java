package cn.bosenkeji.util;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName Success
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String msg = "success";

    @Getter
    @Setter
    private T data;

    public Result() {
        super();
    }

    public Result(T data) {
        super();
        this.data = data;
    }

    public Result(T data, String msg) {
        super();
        this.msg = msg;
    }
}
