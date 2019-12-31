package cn.bosenkeji;

import cn.bosenkeji.messaging.OrderSink;
import cn.bosenkeji.util.RsaUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

import java.util.Map;

/**
 * @ClassName App
 * @Description 启动类
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
**/
@SpringBootApplication
@EnableBinding({OrderSink.class})
@MapperScan("cn.bosenkeji.mapper")
@EnableDiscoveryClient
public class TradeBasicDataApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeBasicDataApp.class);

    public static void main(String[] args) {
        initKey();
        SpringApplication.run(TradeBasicDataApp.class, args);
    }

    private static void initKey(){
        if (!RsaUtils.checkKeyPairOnOSS()){
            //初始化密钥
            //生成密钥对
            LOGGER.info("初始化密钥并生成密钥对");
            try {
                Map<String,String> keyMap = RsaUtils.genKeyPair();
                LOGGER.info("密钥并生成成功");
                LOGGER.info("公钥："+ keyMap.get(RsaUtils.PUBLIC_KEY));
                LOGGER.info("私钥："+ keyMap.get(RsaUtils.PRIVATE_KEY));
                LOGGER.info("密钥保存到OSS");
                RsaUtils.loadKeyPairToOSS(keyMap.get(RsaUtils.PUBLIC_KEY),keyMap.get(RsaUtils.PRIVATE_KEY));
                LOGGER.info("密钥保存成功！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            RsaUtils.downloadPrivateKeyByOSS();
        }
    }
}
