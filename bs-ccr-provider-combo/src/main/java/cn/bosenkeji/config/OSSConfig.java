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
        String accessKeyId = "LTAI4FiW7ykVpsp1C3M2EBzg";
        String accessKeySecret = "hZZmISbQzKUucNPe2woxXFYa3aplaP";
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
