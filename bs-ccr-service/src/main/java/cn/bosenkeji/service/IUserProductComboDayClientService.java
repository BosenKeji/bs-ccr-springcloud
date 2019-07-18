package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IProdcutComboClientServiceFallbackFactory;
import cn.bosenkeji.service.fallback.IUserProdcutComboClientServiceFallbackFactory;
import cn.bosenkeji.service.fallback.IUserProdcutComboDayClientServiceFallbackFactory;
import cn.bosenkeji.vo.ProductCombo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-18 11:29
 */
@FeignClient(name = "bs-ccr-provider-combo",configuration = FeignClientConfig.class,fallbackFactory = IUserProdcutComboDayClientServiceFallbackFactory.class)
public interface IUserProductComboDayClientService {


}
