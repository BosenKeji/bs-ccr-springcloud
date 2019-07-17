package cn.bosenkeji.service;

import cn.bosenkeji.vo.TradePlatformApi;
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

    boolean update(TradePlatformApi tradePlatformApi);

    boolean add(TradePlatformApi tradePlatformApi);

    boolean delete(int id);

}
