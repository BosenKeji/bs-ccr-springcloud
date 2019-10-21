package cn.bosenkeji.service.Impl;

import cn.bosenkeji.exception.AddException;
import cn.bosenkeji.exception.UpdateException;
import cn.bosenkeji.exception.enums.CoinPairChoiceAttributeEnum;
import cn.bosenkeji.mapper.CoinPairChoiceAttributeMapper;
import cn.bosenkeji.service.CoinPairChoiceAttributeService;
import cn.bosenkeji.service.CoinPairChoiceService;
import cn.bosenkeji.service.IStrategyService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.strategy.StrategyOther;
import cn.bosenkeji.vo.transaction.CoinPairChoiceAttribute;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @Author CAJR
 * @create 2019/7/18 10:51
 */
@Service
public class CoinPairChoiceAttributeServiceImpl implements CoinPairChoiceAttributeService {

    private final static int SUCCESS = 1;
    private final static int FAIL = 0;

    @Resource
    CoinPairChoiceAttributeMapper coinPairChoiceAttributeMapper;

    @Resource
    CoinPairChoiceService coinPairChoiceService;

    @Resource
    IStrategyService iStrategyService;

    @Override
    public CoinPairChoiceAttribute getByCoinPartnerChoiceId(int coinPartnerChoiceId) {
        return this.coinPairChoiceAttributeMapper.selectByCoinPartnerChoiceId(coinPartnerChoiceId);
    }

    @Override
    public CoinPairChoiceAttribute get(int id) {
        return this.coinPairChoiceAttributeMapper.selectByPrimaryKey(id);
    }

    @Override
    public Optional<Integer> update(CoinPairChoiceAttribute coinPairChoiceAttribute) {
        return Optional.ofNullable(this.coinPairChoiceAttributeMapper.updateByPrimaryKeySelective(coinPairChoiceAttribute));
    }

    @Override
    public Optional<Integer> add(CoinPairChoiceAttribute coinPairChoiceAttribute) {
        return Optional.ofNullable(this.coinPairChoiceAttributeMapper.insertSelective(coinPairChoiceAttribute));
    }

    @Override
    public Optional<Integer> delete(int coinPairChoiceId) {
        return Optional.ofNullable(this.coinPairChoiceAttributeMapper.deleteByCoinPairChoiceId(coinPairChoiceId));
    }

    @Override
    public Optional<Integer> batchDelete(List<Integer> coinPartnerChoiceIds) {
        return Optional.ofNullable(this.coinPairChoiceAttributeMapper.batchDelete(coinPartnerChoiceIds));
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

        /*查询所有自选币id*/
        List<Integer> dbAllCoinPairChoiceIds = this.coinPairChoiceService.findAllCoinPartnerChoiceId();
        /*验证数据库是否有自选币id*/
        if (!dbAllCoinPairChoiceIds.isEmpty()){
            for (int coinPairChoiceId : coinPairChoiceIds) {
                if (!dbAllCoinPairChoiceIds.contains(coinPairChoiceId)){
                    return Optional.of(FAIL);
                }
            }
        }

        /*查询数据库属性所有自选币id*/
        List<Integer> allCoinPairChoiceIds = this.coinPairChoiceAttributeMapper.findAllCoinPartnerChoiceId();
        /*数据库已存在的自选币id List*/
        List<Integer> existedCoinPairChoiceIds = new ArrayList<>();
        /*数据库不存在的自选币id List*/
        List<Integer> notExistedCoinPairChoiceIds = new ArrayList<>();
        if (!allCoinPairChoiceIds.isEmpty()){
            for (int coinPairChoiceId : coinPairChoiceIds) {
                if (allCoinPairChoiceIds.contains(coinPairChoiceId)){
                    existedCoinPairChoiceIds.add(coinPairChoiceId);
                }else {
                    notExistedCoinPairChoiceIds.add(coinPairChoiceId);
                }
            }
        }else{
            for (int coinPairChoiceId : coinPairChoiceIds) {
                notExistedCoinPairChoiceIds.add(coinPairChoiceId);
            }
        }

        /*插入新的自选币的属性*/
        if (!notExistedCoinPairChoiceIds.isEmpty()){
            notExistedCoinPairChoiceIds.forEach((coinPairChoiceId)->{
                CoinPairChoiceAttribute coinPairChoiceAttribute = new CoinPairChoiceAttribute();

                coinPairChoiceAttribute.setCoinPartnerChoiceId(coinPairChoiceId);
                coinPairChoiceAttribute.setExpectMoney(expectMoney);
                coinPairChoiceAttribute.setStrategyId(strategyId);
                coinPairChoiceAttribute.setIsCustom(isCustom);
                coinPairChoiceAttribute.setStatus(1);
                coinPairChoiceAttribute.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                coinPairChoiceAttribute.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

                add(coinPairChoiceAttribute);
            });
        }
        if (!existedCoinPairChoiceIds.isEmpty()){
            /*查询数据库已存在的自选币属性的List*/
            List<CoinPairChoiceAttribute> coinPairChoiceAttributeList = this.coinPairChoiceAttributeMapper.findSectionCoinPairChoiceAttributeByCoinPartnerChoiceIds(existedCoinPairChoiceIds);
            if (!coinPairChoiceAttributeList.isEmpty()){
                coinPairChoiceAttributeList.forEach(coinPairChoiceAttribute -> {
                    coinPairChoiceAttribute.setExpectMoney(expectMoney);
                    coinPairChoiceAttribute.setStrategyId(strategyId);
                    coinPairChoiceAttribute.setIsCustom(isCustom);
                    coinPairChoiceAttribute.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

                    /*更新已有自选币的属性*/
                    update(coinPairChoiceAttribute);
                });
            }
        }

        return Optional.of(SUCCESS);
    }

    @Override
    public List<Integer> findAllCoinPartnerChoiceId() {
        return this.coinPairChoiceAttributeMapper.findAllCoinPartnerChoiceId();
    }
}
