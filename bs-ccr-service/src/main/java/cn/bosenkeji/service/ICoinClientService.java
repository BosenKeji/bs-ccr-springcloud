package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinClientServiceFallbackFactory;
import cn.bosenkeji.vo.Coin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

@RequestMapping("/coin")
public interface ICoinClientService {

    @GetMapping("/{id}")
    public Coin getCoin(@PathVariable("id")int id);

    @GetMapping("/")
    public List<Coin> listCoin() ;

    @PostMapping("/")
    public boolean addCoin(Coin coin) ;
}
