package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.CoinPairChoiceMapper;
import cn.bosenkeji.service.*;
import cn.bosenkeji.util.CommonConstantUtil;
import cn.bosenkeji.vo.coin.Coin;
import cn.bosenkeji.vo.coin.CoinPair;
import cn.bosenkeji.vo.coin.CoinPairCoin;
import cn.bosenkeji.vo.transaction.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.CollectionUtils;

import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Author CAJR
 * @create 2019/7/17 16:07
 */
@Service
public class CoinPairChoiceServiceImpl implements CoinPairChoiceService {

    @Resource
    CoinPairChoiceMapper coinPairChoiceMapper;

    @Autowired
    ICoinPairCoinClientService iCoinPairCoinClientService;

    @Autowired
    ICoinPairClientService iCoinPairClientService;

    @Autowired
    ICoinClientService iCoinClientService;

    @Autowired
    ICoinPairClientService coinPairClientService;

    @Autowired
    CoinPairChoiceAttributeService coinPairChoiceAttributeService;

    @Autowired
    CoinPairChoiceAttributeCustomServiceImpl coinPairChoiceAttributeCustomService;

    @Autowired
    OrderGroupService orderGroupService;



    @Override
    public PageInfo listByPage(int pageNum, int pageSize,int tradePlatformApiBindProductComboId,int coinId) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<>(fill( tradePlatformApiBindProductComboId, coinId,CommonConstantUtil.ACTIVATE_STATUS));
    }

    @Override
    public PageInfo recordByPage(int pageNum, int pageSize, int tradePlatformApiBindProductComboId, int coinId, String type) {
        PageHelper.startPage(pageNum, pageSize);
        List<CoinPairChoice> coinPairChoices = fill( tradePlatformApiBindProductComboId, coinId,CommonConstantUtil.ALL_STATUS);
        coinPairChoices = filterIsTradeShell(coinPairChoices,type);
        if (!CollectionUtils.isEmpty(coinPairChoices)){
            List<CoinPairChoiceShellOrBuyResult> coinPairChoiceShellOrBuyResults = new ArrayList<>();
            coinPairChoices.forEach(coinPairChoice -> {
                CoinPairChoiceShellOrBuyResult coinPairChoiceShellOrBuyResult = new CoinPairChoiceShellOrBuyResult();
                coinPairChoiceShellOrBuyResult.setCoinPairChoiceId(coinPairChoice.getId());
                coinPairChoiceShellOrBuyResult.setCoinPairName(coinPairChoice.getCoinPair().getName());
                coinPairChoiceShellOrBuyResults.add(coinPairChoiceShellOrBuyResult);
            });
            return new PageInfo<>(coinPairChoiceShellOrBuyResults);
        }
        return  new PageInfo<>();
    }

    private List<CoinPairChoice> fill(int tradePlatformApiBindProductComboId,int coinId,int status) {
        //货币对Map
        Map<Integer, CoinPair> coinPairMap = new HashMap<>(16);
        //根据tradePlatformApiBindProductComboId查询自选币list
        List<CoinPairChoice>  coinPairChoices;
        //根据货币id查询货币对货币的列表
        List<CoinPairCoin> coinPairCoinList = this.iCoinPairCoinClientService.listByCoinId(coinId);
        //真正返回的结果列表
        List<CoinPairChoice> resultCoinPairChoiceList = new ArrayList<>();

        //获取货币对的id的list
        List<Integer> coinPairIds = new ArrayList<>();
        if (!coinPairCoinList.isEmpty()){
            for (CoinPairCoin c : coinPairCoinList) {
                coinPairIds.add(c.getCoinPairId());
            }
        }else {
            return resultCoinPairChoiceList;
        }

        //根据货币对id列表的填充coinPairMap
        if (!coinPairIds.isEmpty()){
            List<CoinPair> coinPairs = this.iCoinPairClientService.findSection(coinPairIds);
            if (!coinPairs.isEmpty()){
                for (CoinPair c : coinPairs) {
                    coinPairMap.put(c.getId(), c);
                }
            }
        }else {
            return resultCoinPairChoiceList;
        }

        //根据TradePlatformApiBindProductComboId填充自选币的货币对数据
        coinPairChoices = fillByTradePlatformApiBindProductComboId(tradePlatformApiBindProductComboId,status);
        if (!coinPairChoices.isEmpty()){
            for (CoinPairChoice c : coinPairChoices) {
                if (coinPairMap.containsKey(c.getCoinPartnerId())){
                    c.setCoinPair(coinPairMap.get(c.getCoinPartnerId()));
                }
            }
            //把货币对不为空的数据填充
           for (CoinPairChoice coinPairChoice : coinPairChoices){
               if (coinPairChoice.getCoinPair() != null){
                   resultCoinPairChoiceList.add(coinPairChoice);
               }
           }
        }

        return filter(coinId,resultCoinPairChoiceList);
    }

    private List<CoinPairChoice> fillByTradePlatformApiBindProductComboId(int tradePlatformApiBindProductComboId,int status){
        if (status == CommonConstantUtil.ACTIVATE_STATUS){
            return this.coinPairChoiceMapper.findByTradePlatformApiBindProductComboIdAndStatus(tradePlatformApiBindProductComboId);
        }
        return this.coinPairChoiceMapper.findAllByTradePlatformApiBindProductComboId(tradePlatformApiBindProductComboId);
    }

    /**
     * 过滤掉不是该计价货币的货币对
     * @param coinId 货币id
     * @param coinPairChoices 填充好的自选币列表
     * @return 过滤好的自选币的列表
     */
    private List<CoinPairChoice> filter(int coinId,List<CoinPairChoice> coinPairChoices){
        Coin coin = iCoinClientService.get(coinId);
        List<CoinPairChoice> result = new ArrayList<>(coinPairChoices);

        String coinName = coin.getName().toUpperCase();
        int coinNameLength = coinName.length();
        for (CoinPairChoice c : coinPairChoices) {
            CoinPair coinPair = c.getCoinPair();

            String coinPairName = coinPair.getName().toUpperCase();
            int coinPairNameLength = coinPairName.length();
            if (coinPairName.lastIndexOf(coinName)+coinNameLength != coinPairNameLength){
                result.remove(c);
            }
        }

        return result;
    }

    /**
     * 过滤掉没有卖买记录的该计价货币的货币对
     * @param coinPairChoices 自选币的列表
     */
    private List<CoinPairChoice> filterIsTradeShell(List<CoinPairChoice> coinPairChoices, String type) {
        if (CollectionUtils.isEmpty(coinPairChoices)){
            return null;
        }

        List<CoinPairChoice> resultCoinPairChoices = new ArrayList<>();
        List<Integer> coinPairChoiceIds = new ArrayList<>();
        coinPairChoices.forEach(coinPairChoice -> {
            coinPairChoiceIds.add(coinPairChoice.getId());
        });

        List<OrderGroup> orderGroups = new ArrayList<>();
        if (!coinPairChoiceIds.isEmpty()){
            orderGroups = this.orderGroupService.partFindByCoinPairChoiceIds(coinPairChoiceIds);
        }

        if (!type.isEmpty() && !orderGroups.isEmpty()){
            if (type.equals(CommonConstantUtil.RECORD_PROFIT)){
                orderGroups.forEach(orderGroup -> {
                    if (orderGroup.getIsEnd() == CommonConstantUtil.ORDER_GROUP_IS_END && orderGroup.getEndType() != CommonConstantUtil.ORDER_GROUP_FORGET){
                        resultCoinPairChoices.add(orderGroup.getCoinPairChoice());
                    }
                });
            }
            if (type.equals(CommonConstantUtil.RECORD_BUY)){
                orderGroups.forEach(orderGroup -> {
                    resultCoinPairChoices.add(orderGroup.getCoinPairChoice());
                });
            }
        }

        //去重
        List<CoinPairChoice> unique =  resultCoinPairChoices.stream().collect(
                collectingAndThen(
                        toCollection(() -> new TreeSet<>(comparingLong(CoinPairChoice::getId))), ArrayList::new)
        );

        List<Integer> uniqueIdResult = new ArrayList<>();
        unique.forEach(coinPairChoice -> uniqueIdResult.add(coinPairChoice.getId()));
        List<CoinPairChoice> result = new ArrayList<>();
        coinPairChoices.forEach(coinPairChoice -> {
            if (uniqueIdResult.contains(coinPairChoice.getId())){
                result.add(coinPairChoice);
            }
        });

        return result;
    }

    @Override
    public CoinPairChoice get(int id) {
        CoinPairChoice coinPairChoice = this.coinPairChoiceMapper.selectByPrimaryKey(id);
        if (coinPairChoice != null){
            CoinPair coinPair = this.iCoinPairClientService.getCoinPair(coinPairChoice.getCoinPartnerId());
            coinPairChoice.setCoinPair(coinPair);
        }
        return coinPairChoice;
    }

    @Override
    public Optional<Integer> add(CoinPairChoice coinPairChoice) {
        Integer coinPairChoiceId = coinPairChoiceMapper.selectIdByCoinPartnerIdAndRobotIdAndStatus(coinPairChoice.getCoinPartnerId(),coinPairChoice.getTradePlatformApiBindProductComboId());
        if (coinPairChoiceId != null){
            if (coinPairChoiceId >0){
                return Optional.ofNullable(this.coinPairChoiceMapper.updateStatusByPrimaryKey(coinPairChoiceId,Timestamp.valueOf(LocalDateTime.now()),CommonConstantUtil.ACTIVATE_STATUS));
            }
        }

        return Optional.ofNullable(coinPairChoiceMapper.insertSelective(coinPairChoice));
    }

    @Override
    public Optional<Integer> update(CoinPairChoice coinPairChoice) {
        return Optional.ofNullable(coinPairChoiceMapper.updateByPrimaryKeySelective(coinPairChoice));
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public Optional<Integer> delete(int id) {
        try{
            if (this.coinPairChoiceAttributeService.checkByCoinPartnerChoiceId(id).get() == 1){
                this.coinPairChoiceAttributeService.delete(id);
            }

            if (this.coinPairChoiceAttributeCustomService.checkByCoinPartnerChoiceId(id).get() == 1){
                this.coinPairChoiceAttributeCustomService.deleteByCoinPairChoiceId(id);
            }

            coinPairChoiceMapper.updateStatusByPrimaryKey(id,Timestamp.valueOf(LocalDateTime.now()), CommonConstantUtil.DELETE_STATUS);
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Optional.of(CommonConstantUtil.FAIL);
        }

        return Optional.of(CommonConstantUtil.SUCCESS);
    }

    @Override
    public Optional<Integer> checkExistByCoinPartnerNameAndRobotId(String coinPairName, int tradePlatformApiBindProductComboId) {
        CoinPair coinPair = new CoinPair();
        if (this.iCoinPairClientService.getCoinPairByName(coinPairName) != null){
            coinPair = this.iCoinPairClientService.getCoinPairByName(coinPairName);
        }
        return Optional.of(this.coinPairChoiceMapper.checkExistByCoinPartnerIdAndRobotIdAndStatus(coinPair.getId(), tradePlatformApiBindProductComboId));
    }

    @Override
    public Optional<Integer> checkExistByCoinPartnerIdAndRobotId(int coinPairId, int tradePlatformApiBindProductComboId) {
        return Optional.ofNullable(this.coinPairChoiceMapper.checkExistByCoinPartnerIdAndRobotIdAndStatus(coinPairId, tradePlatformApiBindProductComboId));
    }

    @Override
    public List<CoinPairChoice> findAll() {
        List<CoinPairChoice> coinPairChoiceList = coinPairChoiceMapper.findAll();
        List<CoinPair> coinPairList = coinPairClientService.findAll();
        if (!CollectionUtils.isEmpty(coinPairChoiceList) && !CollectionUtils.isEmpty(coinPairList)) {
            for (CoinPairChoice c : coinPairChoiceList) {
                Optional<CoinPair> first = coinPairList.stream().filter((v) -> v.getId() == c.getCoinPartnerId()).findFirst();
                c.setCoinPair(first.get());
            }
        }
        return coinPairChoiceList;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
    public Optional<Integer> batchDelete(String idStr,int tradePlatformApiBindProductComboId) {
        //有属性的自选币id集合
        List<Integer> coinPairChoiceAttributeList = new ArrayList<>();

        //转格式
        String[] coinPairChoiceIdStr = idStr.split(",");
        List<Integer> coinPairChoiceIds = new ArrayList<>();
        if (coinPairChoiceIdStr.length != 0){
            for (String s : coinPairChoiceIdStr) {
                coinPairChoiceIds.add(Integer.valueOf(s));
            }
        }

        //验证verification
        List<CoinPairChoice> coinPairChoices = this.coinPairChoiceMapper.findAll();
        //筛选出等于tradePlatformApiBindProductComboId的自选币
        List<CoinPairChoice> coinPairChoicesByTradePlatformApiBindProductComboId;
        //筛选出等于tradePlatformApiBindProductComboId的自选币id
        List<Integer> coinPairChoiceIdList = new ArrayList<>();
        if (!coinPairChoices.isEmpty()){
            coinPairChoicesByTradePlatformApiBindProductComboId = coinPairChoices.stream().filter(coinPairChoice -> coinPairChoice.getTradePlatformApiBindProductComboId() == tradePlatformApiBindProductComboId).collect(Collectors.toList());
            if (!coinPairChoicesByTradePlatformApiBindProductComboId.isEmpty()){
                coinPairChoicesByTradePlatformApiBindProductComboId.forEach(coinPairChoice -> {
                    coinPairChoiceIdList.add(coinPairChoice.getId());
                });
            }else {
                return Optional.of(CommonConstantUtil.FAIL);
            }
        }
        if (!coinPairChoiceIdList.isEmpty()) {
            for (Integer id : coinPairChoiceIds) {
                if (!coinPairChoiceIdList.contains(id)){
                    return Optional.of(CommonConstantUtil.FAIL);
                }
            }
        }

        //分开没有属性的自选币id
        //所有自选币属性中的自选币id
        List<Integer> coinPairChoiceAttributeIdList = this.coinPairChoiceAttributeService.findAllCoinPartnerChoiceId();
        if (!coinPairChoiceAttributeIdList.isEmpty()){
            for (Integer id : coinPairChoiceIds) {
                if (coinPairChoiceAttributeIdList.contains(id)){
                    coinPairChoiceAttributeList.add(id);
                }
            }
        }

        //删除，手动回滚
        if (!coinPairChoiceAttributeList.isEmpty()){
            try{
                this.coinPairChoiceAttributeService.batchDelete(coinPairChoiceAttributeList);
                this.coinPairChoiceAttributeCustomService.batchDelete(coinPairChoiceAttributeList);
                this.coinPairChoiceMapper.batchDelete(coinPairChoiceIds);
            }catch (Exception e){
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Optional.of(CommonConstantUtil.FAIL);
            }
        }else{
            try{
                this.coinPairChoiceMapper.batchUpdateStatusByPrimaryKey(coinPairChoiceIds,Timestamp.valueOf(LocalDateTime.now()));
            }catch (Exception e){
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return Optional.of(CommonConstantUtil.FAIL);
            }
        }
        return Optional.of(CommonConstantUtil.SUCCESS);
    }

    @Override
    public List<Integer> findAllCoinPartnerChoiceId() {
        return this.coinPairChoiceMapper.findAllCoinPairChoiceId();
    }

    @Override
    public CoinPairChoicePositionDetailResult getCoinPairChoicePositionDetail(int coinPairChoiceId) {
        CoinPairChoicePositionDetailResult result = new CoinPairChoicePositionDetailResult();
        CoinPairChoice coinPairChoice = get(coinPairChoiceId);

        if (coinPairChoice == null || coinPairChoice.getCoinPair() == null){
            return null;
        }

        result.setCoinPairChoiceName(coinPairChoice.getCoinPair().getName());
        try {
            List<TradeOrder> tradeOrders = this.orderGroupService.getByCoinPairChoiceId(coinPairChoiceId).getTradeOrders().stream().filter(tradeOrder -> tradeOrder.getTradeType() == 1).collect(Collectors.toList());
            result.setTradeOrders(tradeOrders);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return result;
        }
    }
}
