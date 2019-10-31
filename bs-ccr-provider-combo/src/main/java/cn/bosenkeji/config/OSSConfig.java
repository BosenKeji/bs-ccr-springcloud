package cn.bosenkeji.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OSSConfig {

    @Bean
    public OSS ossClient() {
        String endpoint = "oss-cn-shenzhen.aliyuncs.com";
        String accessKeyId = "LTAISwQkitLFbG0t";
        String accessKeySecret = "aCeWQl874e6Jvh76GtfhEAeC24hKl0";
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
