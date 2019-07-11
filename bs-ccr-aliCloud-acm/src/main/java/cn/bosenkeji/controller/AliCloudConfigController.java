package cn.bosenkeji.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @ClassName AliCloudACMController
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/

@RestController
@RequestMapping("/config")
@RefreshScope
public class AliCloudConfigController {

    @Value("${connectTimeoutInMills:5000}")
    private int connectTimeoutInMills;

    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
    public int get() {
        return connectTimeoutInMills;
    }
}
