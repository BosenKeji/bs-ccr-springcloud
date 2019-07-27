package cn.bosenkeji.service;

import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/15 14:56
 */
public interface TradePlatformApiService {
    List<TradePlatformApi> list();

    PageInfo listByPage(int pageNum,int pageSize);

    Optional<TradePlatformApi> get(int id);

    Optional<Integer> update(TradePlatformApi tradePlatformApi);

    Optional<Integer> add(TradePlatformApi tradePlatformApi);

    Optional<Integer> delete(int id);

}
