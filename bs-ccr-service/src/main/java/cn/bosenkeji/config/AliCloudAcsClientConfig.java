package cn.bosenkeji.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName AliCloudServerConfig
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@Configuration
public class AliCloudAcsClientConfig {

    @Bean
    public IAcsClient iAcsClient(){
        DefaultProfile profile = DefaultProfile.
                getProfile(
                        "cn-shenzhen",
                        "xxx",
                        "cccc");
        IAcsClient iAcsClient = new DefaultAcsClient(profile);

        return iAcsClient;
    }
}
