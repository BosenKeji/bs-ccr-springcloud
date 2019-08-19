package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.coin.Coin;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName ICoinClientServiceFallbackFactory
 * @Description TODO
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
 **/
@Component
public class ICoinClientServiceFallbackFactory implements FallbackFactory<ICoinClientService> {
    @Override
    public ICoinClientService create(Throwable throwable) {
        return new ICoinClientService() {
            @Override
            public Coin get(int id) {
                Coin coin = new Coin();
                coin.setName("hystrixName");
                return coin;
            }

            @Override
            public PageInfo list(int pageNum, int pageSizeCommon) {
                Coin coin = new Coin();
                coin.setName("hystrixName");
                List<Coin> listCoin = new ArrayList<Coin>();
                listCoin.add(coin);
                return new PageInfo(listCoin);
            }

            @Override
            public Result add(Coin coin) {
                return new Result<>(0,"hystrix fail");
            }

            @Override
            public Result update(Coin coin) {
                return new Result<>(0,"hystrix fail");
            }

            @Override
            public Result delete(int id) {
                return new Result<>(0,"hystrix fail");
            }


        };
    }
}
