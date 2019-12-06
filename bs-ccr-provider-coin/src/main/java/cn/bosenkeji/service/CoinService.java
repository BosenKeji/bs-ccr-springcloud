package cn.bosenkeji.service;

import cn.bosenkeji.vo.coin.Coin;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName CoinService
 * @Description 货币服务接口
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
**/
public interface CoinService {
    List<Coin> list();

    PageInfo listByPage(int pageNum, int pageSize);

    Optional<Integer> checkExistByName(String name);

    Coin get(int id);

    Coin getByName(String name);

    Optional<Integer> add(Coin coin);

    Optional<Integer> update(Coin coin);

    Optional<Integer> delete(int id);

}
