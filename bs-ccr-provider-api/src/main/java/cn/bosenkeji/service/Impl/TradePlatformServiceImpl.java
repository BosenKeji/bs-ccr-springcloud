package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.TradePlatformMapper;
import cn.bosenkeji.service.TradePlatformService;
import cn.bosenkeji.vo.TradePlatform;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
}
