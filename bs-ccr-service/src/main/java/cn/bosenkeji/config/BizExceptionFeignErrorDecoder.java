package cn.bosenkeji.config;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author CAJR
 * @date 2020/1/8 11:10 上午
 */
//@Configuration
public class BizExceptionFeignErrorDecoder implements feign.codec.ErrorDecoder {
    private static final Logger logger = LoggerFactory.getLogger(BizExceptionFeignErrorDecoder.class);

    @Override
    public Exception decode(String s, Response response) {
//        switch(response.status()) {
//            case 400:
//                return new HystrixBadRequestException("错误的请求");
//            case 401:
//                return new HystrixBadRequestException("未经授权");
//            case 403:
//                return new HystrixBadRequestException("Forbidden");
//            case 404:
//                return new HystrixBadRequestException("NotFound");
//            case 405:
//                return new HystrixBadRequestException("MethodNotAllowed");
//            case 406:
//                return new HystrixBadRequestException("NotAcceptable");
//            case 409:
//                return new HystrixBadRequestException("Conflict");
//            case 410:
//                return new HystrixBadRequestException("Gone");
//            case 415:
//                return new HystrixBadRequestException("UnsupportedMediaType");
//            case 422:
//                return new HystrixBadRequestException("UnprocessableEntity");
//            case 429:
//                return new HystrixBadRequestException("TooManyRequests");
//        }
        return feign.FeignException.errorStatus(s, response);
    }
}
