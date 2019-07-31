package cn.bosenkeji.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName ApiGatewayConfig
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@Configuration
public class ApiGatewayConfig {

    @Value("${api.regionId}")
    private  String regionId ;

    @Value("${api.accessKeyId}")
    private String accessKeyId;

    @Value("${api.accessSecret}")
    private String accessSecret;

    @Bean
    public IAcsClient setClient(){
        DefaultProfile profile = DefaultProfile.getProfile(regionId,accessKeyId,accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

}
