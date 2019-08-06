package cn.bosenkeji.service.impl;

import cn.bosenkeji.exception.AddException;
import cn.bosenkeji.exception.UpdateException;
import cn.bosenkeji.exception.enums.CoinPairChoiceAttributeEnum;
import cn.bosenkeji.mapper.CoinPairChoiceAttributeMapper;
import cn.bosenkeji.service.CoinPairChoiceAttributeService;
import cn.bosenkeji.service.IStrategyService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.strategy.StrategyOther;
import cn.bosenkeji.vo.transaction.CoinPairChoiceAttribute;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/18 10:51
 */
@Service
public class CoinPairChoiceAttributeServiceImpl implements CoinPairChoiceAttributeService {

    @Resource
    CoinPairChoiceAttributeMapper coinPairChoiceAttributeMapper;

    @Resource
    IStrategyService iStrategyService;

    @Override
    public CoinPairChoiceAttribute getByCoinPartnerChoiceId(int coinPartnerChoiceId) {
        return this.coinPairChoiceAttributeMapper.selectByCoinPartnerChoiceId(coinPartnerChoiceId);
    }

    @Override
    public Optional<CoinPairChoiceAttribute> get(int id) {
        return Optional.ofNullable(this.coinPairChoiceAttributeMapper.selectByCoinPartnerChoiceId(id));
    }

    @Override
    public Optional<Integer> update(CoinPairChoiceAttribute coinPairChoiceAttribute) {
        return Optional.ofNullable(this.coinPairChoiceAttributeMapper.updateByCoinPartnerChoiceIdSelective(coinPairChoiceAttribute));
    }

    @Override
    public Optional<Integer> add(CoinPairChoiceAttribute coinPairChoiceAttribute) {
        return Optional.ofNullable(this.coinPairChoiceAttributeMapper.insertSelective(coinPairChoiceAttribute));
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.ofNullable(this.coinPairChoiceAttributeMapper.deleteByPrimaryKey(id));
    }

    @Override
    public Optional<Integer> checkByCoinPartnerChoiceId(int coinPartnerChoiceId) {
        return Optional.ofNullable(this.coinPairChoiceAttributeMapper.checkByCoinPartnerChoiceId(coinPartnerChoiceId));
    }

    @Override
    public Optional<Integer> setting(String coinPairChoiceIdStr, int strategyId, int money, int isCustom) {
        //字符串切割
        String [] coinPairChoiceIdStrings = coinPairChoiceIdStr.split(",");

        int[] coinPairChoiceIds=new int[coinPairChoiceIdStrings.length];
        for (int i=0;i<coinPairChoiceIdStrings.length;i++){
            coinPairChoiceIds[i]=Integer.parseInt(coinPairChoiceIdStrings[i]);
        }

        /*根据strategyId查询StrategyOther*/
        StrategyOther strategyOther = this.iStrategyService.getStrategy(strategyId);

        int lever = strategyOther.getLever();
        int expectMoney=(money*lever)/coinPairChoiceIds.length;

        for (int coinPairChoiceId : coinPairChoiceIds){
            CoinPairChoiceAttribute coinPairChoiceAttribute =getByCoinPartnerChoiceId(coinPairChoiceId);

            /* 数据库已存在的就直接更新其预算和更新时间*/
            if (coinPairChoiceAttribute != null){
                coinPairChoiceAttribute.setExpectMoney(expectMoney);
                coinPairChoiceAttribute.setStrategyId(strategyId);
                coinPairChoiceAttribute.setIsCustom(isCustom);
                coinPairChoiceAttribute.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

                /*更新已有自选币的属性*/
                this.coinPairChoiceAttributeMapper.updateByPrimaryKeySelective(coinPairChoiceAttribute);

            }else {
                CoinPairChoiceAttribute coinPairChoiceAttribute1 = new CoinPairChoiceAttribute();
                coinPairChoiceAttribute1.setCoinPartnerChoiceId(coinPairChoiceId);
                coinPairChoiceAttribute1.setStrategyId(strategyId);
                coinPairChoiceAttribute1.setIsCustom(isCustom);
                coinPairChoiceAttribute1.setStatus(1);
                coinPairChoiceAttribute1.setExpectMoney(expectMoney);
                coinPairChoiceAttribute1.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                coinPairChoiceAttribute1.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

                return Optional.ofNullable(this.coinPairChoiceAttributeMapper.insert(coinPairChoiceAttribute1));
            }
        }

        return Optional.ofNullable(1);
    }
}
