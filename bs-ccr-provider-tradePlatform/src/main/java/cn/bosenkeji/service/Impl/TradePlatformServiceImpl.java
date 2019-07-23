package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.TradePlatformMapper;
import cn.bosenkeji.service.TradePlatformService;
import cn.bosenkeji.vo.tradeplateform.TradePlatform;
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
    public Optional<TradePlatform> get(int id) {
        return Optional.ofNullable(tradePlatformMapper.selectByPrimaryKey(id));
    }

    @Override
    public boolean add(TradePlatform tradePlatform) {
        return tradePlatformMapper.insertSelective(tradePlatform) == 1;
    }

    @Override
    public boolean update(TradePlatform tradePlatform) {
        return tradePlatformMapper.updateByPrimaryKeySelective(tradePlatform)==1;
    }

    @Override
    public boolean delete(int id) {
        return tradePlatformMapper.deleteByPrimaryKey(id) == 1;
    }
}
