package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairDealService;
import cn.bosenkeji.vo.CoinPairDeal;
import cn.bosenkeji.vo.CoinPairDealVO;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            public PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndChoiceId(Integer userId, Integer choiceId, Integer pageNum, Integer pageSize) {
                return hystrixResult(userId);
            }

            @Override
            public PageInfo<CoinPairDealVO> findCoinPairDealByUserIdAndType(Integer userId, Integer type, Integer pageNum, Integer pageSize) {
                return hystrixResult(userId);
            }

            @Override
            public Optional<Integer> updateCoinPairDealStartsById(Integer id, Integer status) {
                Optional<Integer> result = Optional.of(0);
                return result;
            }

            @Override
            public Optional<Integer> insertCoinPairDealBySelective(CoinPairDeal coinPairDeal) {
                Optional<Integer> result = Optional.of(0);
                return result;
            }

            @Override
            public Optional<Integer> countCoinPair(Integer userId) {
                Optional<Integer> result = Optional.of(0);
                return result;
            }

            @Override
            public Optional<Integer> countCoinPairDeal(Integer userId, Integer choiceId) {
                Optional<Integer> result = Optional.of(0);
                return result;
            }

            @Override
            public Optional<Integer> deleteCoinPairDealByPrimaryKey(Integer id) {
                Optional<Integer> result = Optional.of(0);
                return result;
            }

            @Override
            public Optional<Integer> deleteBatchCoinPairDealByUserIdAndChoiceId(Integer userId, Integer choiceId) {
                Optional<Integer> result = Optional.of(0);
                return result;
            }
        };
    }
}
