package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairDealService;
import cn.bosenkeji.vo.CoinPairDeal;
import cn.bosenkeji.vo.CoinPairDealVO;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ICoinPairDealServiceFallbackFactory implements FallbackFactory<ICoinPairDealService> {


    public static PageInfo<CoinPairDealVO> hystrixResult(Integer userId){
        PageInfo pageInfo = new PageInfo();
        List<CoinPairDealVO> list = new ArrayList<>();
        CoinPairDealVO coinPairDealVO = new CoinPairDealVO(userId,0,0,0,0);
        list.add(coinPairDealVO);
        pageInfo.setList(list);
        return pageInfo;
    }


    @Override
    public ICoinPairDealService create(Throwable throwable) {
        return new ICoinPairDealService() {
            @Override
            public PageInfo<CoinPairDealVO> findCoinPairDealByUserId(Integer userId, Integer pageNum, Integer pageSize) {
                return hystrixResult(userId);
            }

            @Override
            public PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndChoicId(Integer userId, Integer choicId, Integer pageNum, Integer pageSize) {
                return hystrixResult(userId);
            }

            @Override
            public PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndType(Integer userId, Integer type, Integer pageNum, Integer pageSize) {
                return hystrixResult(userId);
            }

            @Override
            public boolean updateCoinPairDealStartsById(Integer id, Integer status) {
                return false;
            }

            @Override
            public boolean insertCoinPairDealBySelective(CoinPairDeal coinPairDeal) {
                return false;
            }

            @Override
            public int countCoinPair(Integer userId) {
                return 0;
            }

            @Override
            public int countCoinPairDeal(Integer userId, Integer choicId) {
                return 0;
            }
        };
    }
}
