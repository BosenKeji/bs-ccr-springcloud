package cn.bosenkeji.config;

import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author CAJR
 * @date 2020/1/10 11:01 上午
 */
//@Configuration
public class BizExceptionFeignErrorDecoder implements feign.codec.ErrorDecoder {
    private static final Logger logger = LoggerFactory.getLogger(BizExceptionFeignErrorDecoder.class);

    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 404) {
            logger.info("404");
            return null;
        }
        return feign.FeignException.errorStatus(s, response);
    }
}
