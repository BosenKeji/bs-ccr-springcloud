package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ICoinPairDealService;
import cn.bosenkeji.vo.transaction.CoinPairDeal;
import cn.bosenkeji.vo.transaction.CoinPairDealOther;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@Component
public class ICoinPairDealServiceFallbackFactory implements FallbackFactory<ICoinPairDealService> {


    public static PageInfo<CoinPairDealOther> hystrixResult(Integer userId){
        PageInfo pageInfo = new PageInfo();
        List<CoinPairDealOther> list = new ArrayList<>();
        CoinPairDealOther coinPairDealOther = new CoinPairDealOther(userId,0,0,0,0);
        list.add(coinPairDealOther);
        pageInfo.setList(list);
        return pageInfo;
    }


    @Override
    public ICoinPairDealService create(Throwable throwable) {
        return new ICoinPairDealService() {
            @Override
            public PageInfo<CoinPairDealOther> findCoinPairDealByUserId(Integer userId, Integer pageNum, Integer pageSize) {
                return hystrixResult(userId);
            }

            @Override
            public PageInfo<CoinPairDealOther> findCoinPairDealByUserIdAndChoiceId(Integer userId, Integer choiceId, Integer pageNum, Integer pageSize) {
                return hystrixResult(userId);
            }

            @Override
            public PageInfo<CoinPairDealOther> findCoinPairDealByUserIdAndType(Integer userId, Integer type, Integer pageNum, Integer pageSize) {
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
