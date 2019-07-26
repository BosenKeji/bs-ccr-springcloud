package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.TradePlatformMapper;
import cn.bosenkeji.service.TradePlatformService;
import cn.bosenkeji.vo.tradplateform.TradePlatform;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @Author CAJR
 * @create 2019/7/15 18:02
 */
@Service
public class TradePlatformServiceImpl implements TradePlatformService {

    @Resource
    TradePlatformMapper tradePlatformMapper;


    @Override
    public List<TradePlatform> list() {
        return tradePlatformMapper.findAll();
    }

    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo(list());
    }

    @Override
    public PageInfo listByPageAndUserId(int pageNum, int pageSize, int userId) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo(this.tradePlatformMapper.findAllByUserId(userId));
    }

    @Override
    public Optional<TradePlatform> get(int id) {
        return Optional.ofNullable(tradePlatformMapper.selectByPrimaryKey(id));
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
