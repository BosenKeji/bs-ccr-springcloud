package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.TradePlatformMapper;
import cn.bosenkeji.service.ICoinPairClientService;
import cn.bosenkeji.service.TradePlatformApiService;
import cn.bosenkeji.service.TradePlatformCoinPairService;
import cn.bosenkeji.service.TradePlatformService;
import cn.bosenkeji.vo.coin.CoinPair;
import cn.bosenkeji.vo.tradeplatform.TradePlatform;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import cn.bosenkeji.vo.tradeplatform.TradePlatformCoinPair;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author CAJR
 * @create 2019/7/15 18:02
 */
@Service
public class TradePlatformServiceImpl implements TradePlatformService {

    @Resource
    TradePlatformMapper tradePlatformMapper;

    @Resource
    TradePlatformCoinPairService tradePlatformCoinPairService;

    @Resource
    ICoinPairClientService iCoinPairClientService;



    @Override
    public List<TradePlatform> list() {
        return tradePlatformMapper.findAll();
    }

    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<TradePlatform> tradePlatforms = list();
        return new PageInfo<>(tradePlatforms);
    }


    private void fill(TradePlatform tradePlatform, List<Integer> coinPairIds) {
        if (!coinPairIds.isEmpty()){
            List<CoinPair> coinPairs = this.iCoinPairClientService.findSection(coinPairIds);
            tradePlatform.setCoinPairs(coinPairs);
        }
    }


    @Override
    public TradePlatform get(int id) {
        TradePlatform tradePlatform = tradePlatformMapper.selectByPrimaryKey(id);
        if (tradePlatform != null){
            List<TradePlatformCoinPair> tradePlatformCoinPairs = tradePlatform.getTradePlatformCoinPairs();
            List<Integer> coinPairIds = new ArrayList<>();
            if (!tradePlatformCoinPairs.isEmpty()){
                for (TradePlatformCoinPair t: tradePlatformCoinPairs) {
                    coinPairIds.add(t.getCoinPairId());
                }
                fill(tradePlatform,coinPairIds);
            }
        }

        return tradePlatform;
    }

    @Override
    public Optional<Integer> add(TradePlatform tradePlatform) {
        return Optional.ofNullable(tradePlatformMapper.insertSelective(tradePlatform));
    }

    @Override
    public Optional<Integer> update(TradePlatform tradePlatform) {
        return Optional.ofNullable(tradePlatformMapper.updateByPrimaryKeySelective(tradePlatform));
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.ofNullable(tradePlatformMapper.deleteByPrimaryKey(id));
    }

    @Override
    public Optional<Integer> checkExistByName(String name) {
        return Optional.ofNullable(this.tradePlatformMapper.checkExistByName(name));
    }
}
