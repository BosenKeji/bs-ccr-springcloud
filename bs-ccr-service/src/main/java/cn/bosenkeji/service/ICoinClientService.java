package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinClientServiceFallbackFactory;
import cn.bosenkeji.vo.Coin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName ICoinClientService
 * @Description TODO
 * @Author Yu XueWen
 * @Email yuxuewen23@qq.com
 * @Versio V1.0
 **/
@FeignClient(name = "BS-CCR-PROVIDER-COIN",configuration = FeignClientConfig.class,fallbackFactory = ICoinClientServiceFallbackFactory.class)
public interface ICoinClientService {
    @RequestMapping("/coin/get/{id}")
    public Coin getCoin(@PathVariable("id")int id);

    @RequestMapping("/coin/list")
    public List<Coin> listCoin() ;

    @RequestMapping("/coin/add")
    public boolean addCoin(Coin coin) ;
}
