package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinClientServiceFallbackFactory;
import cn.bosenkeji.vo.Coin;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ICoinClientService
 * @Description TODO
 * @Author Yu XueWen
 * @Email yuxuewen23@qq.com
 * @Versio V1.0
 **/
@FeignClient(name = "bs-ccr-provider-coin",configuration = FeignClientConfig.class,fallbackFactory = ICoinClientServiceFallbackFactory.class)
public interface ICoinClientService {

    @GetMapping("/coin/{id}")
    public Coin getCoin(@PathVariable("id")int id);

    @GetMapping("/coin/")
    public PageInfo listCoin(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                             @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon) ;

    @PostMapping("/coin/")
    public boolean addCoin(@RequestBody Coin coin) ;

    @PutMapping("/coin/")
    public boolean updateCoin(@RequestBody Coin coin);

    @DeleteMapping("/coin/{id}")
    public boolean deleteCoin(@PathVariable("id") int id );
}
