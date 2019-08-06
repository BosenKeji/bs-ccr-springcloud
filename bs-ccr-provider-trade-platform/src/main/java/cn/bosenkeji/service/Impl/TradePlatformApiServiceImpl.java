package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.TradePlatformApiMapper;
import cn.bosenkeji.service.TradePlatformApiService;
import cn.bosenkeji.service.TradePlatformService;
import cn.bosenkeji.vo.tradeplatform.TradePlatform;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/15 14:59
 */

@Service
public class TradePlatformApiServiceImpl implements TradePlatformApiService {

    @Resource
    TradePlatformApiMapper tradePlatformApiMapper;

    @Resource
    TradePlatformService tradePlatformService;

    @Override
    public PageInfo listByPage(int pageNum, int pageSize,int userId) {
        PageHelper.startPage(pageNum,pageSize);
        List<TradePlatformApi> tradePlatformApis = this.tradePlatformApiMapper.findAllByUserId(userId);
        if (!tradePlatformApis.isEmpty()){
            fill(tradePlatformApis);
        }
        return new PageInfo(tradePlatformApis);
    }

    private void fill(List<TradePlatformApi> tradePlatformApis) {
        for (TradePlatformApi t : tradePlatformApis) {
            TradePlatform tradePlatform = this.tradePlatformService.get(t.getTradePlatformId());
            t.setTradePlatform(tradePlatform);
        }
    }

    @Override
    public TradePlatformApi get(int id) {
        return tradePlatformApiMapper.selectByPrimaryKey(id);
    }

    @Override
    public TradePlatformApi getByTradePlatformIdAndUserId(int tradePlatformId, int userId) {
        return tradePlatformApiMapper.selectByTradePlatformIdAndUserId(tradePlatformId, userId);
    }

    @Override
    public Optional<Integer> update(TradePlatformApi tradePlatformApi) {
        return Optional.ofNullable(tradePlatformApiMapper.updateByPrimaryKeySelective(tradePlatformApi));
    }

    @Override
    public Optional<Integer> add(TradePlatformApi tradePlatformApi) {
        return Optional.ofNullable(tradePlatformApiMapper.insertSelective(tradePlatformApi));
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.ofNullable(tradePlatformApiMapper.deleteByPrimaryKey(id)) ;
    }

    @Override
    public TradePlatformApi getByUserId(int userId) {
        return tradePlatformApiMapper.selectByUserId(userId);
    }

    @Override
    public Optional<Integer> checkExistByTradePlatformIdAndUserId(int tradePlatformId, int userId) {
        return Optional.ofNullable(tradePlatformApiMapper.checkExistByTradePlatformIdAndUserId(tradePlatformId, userId));
    }
}
