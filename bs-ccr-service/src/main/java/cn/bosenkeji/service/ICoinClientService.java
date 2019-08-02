package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.ICoinClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.Coin;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @ClassName ICoinClientService
 * @Description TODO
 * @Author Yu XueWen
 * @Email yuxuewen23@qq.com
 * @Versio V1.0
 **/
@FeignClient(name = "bs-ccr-provider-trade-basic-data",configuration = FeignClientConfig.class,fallbackFactory = ICoinClientServiceFallbackFactory.class)
public interface ICoinClientService {

    @GetMapping("/coin/{id}")
    public Coin get(@PathVariable("id")int id);

    @GetMapping("/coin/")
    public PageInfo list(@RequestParam( value="pageNum",defaultValue="1") int pageNum,
                             @RequestParam(value = "pageSizeCommon",defaultValue = "10") int pageSizeCommon) ;

    @PostMapping("/coin/")
    public Result add(@RequestBody Coin coin) ;

    @PutMapping("/coin/")
    public Result update(@RequestBody Coin coin);

    @DeleteMapping("/coin/{id}")
    public Result delete(@PathVariable("id") int id );
}
