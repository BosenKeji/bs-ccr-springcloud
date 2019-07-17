package cn.bosenkeji.service;

import cn.bosenkeji.vo.TradePlatform;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/15 18:02
 */
public interface TradePlatformService {

    List<TradePlatform> list();

    /**
     * @param pageNum
     * @param pageSize
     * @return PageInfo
     */
    PageInfo listByPage(int pageNum, int pageSize);

    Optional<TradePlatform> get(int id);

    boolean add(TradePlatform tradePlatform);

    boolean update(TradePlatform tradePlatform);

    boolean delete(int id);
}
