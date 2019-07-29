package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.TradePlatformApiBindProductComboMapper;
import cn.bosenkeji.service.IUserProductComboClientService;
import cn.bosenkeji.service.TradePlatformApiBindProductComboService;
import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductCombo;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service.Impl
 * @Version V1.0
 * @create 2019-07-26 15:47
 */
@Service
public class TradePlatformApiBindProductComboServiceImpl implements TradePlatformApiBindProductComboService {

    @Autowired
    private TradePlatformApiBindProductComboMapper tradePlatformApiBindProductComboMapper;

    //注入用户套餐生产者
    @Resource
    private IUserProductComboClientService iUserProductComboClientService;

    @Override
    public PageInfo<TradePlatformApiBindProductCombo> findByUserIdWithPage(int userId, int pageNum, int pageSize) {

        //查询用户下的所有的套餐
        //PageInfo pageInfo = this.iUserProductComboClientService.listByUserId(userId, pageNum, pageSize);

        //查询用户下的
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<TradePlatformApiBindProductCombo> tradePlatformApiBindProductComboPageInfo =
                new PageInfo<>(tradePlatformApiBindProductComboMapper.findByUserId(userId));
        List<TradePlatformApiBindProductCombo> pageList = tradePlatformApiBindProductComboPageInfo.getList();

        //分别查询对应的用户套餐——调用用户套餐的服务
        for (TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo : pageList) {
            int upc_id=tradePlatformApiBindProductCombo.getUserProductComboId();
            if(upc_id>0) {
                UserProductCombo userProductCombo = iUserProductComboClientService.getUserProductCombo(tradePlatformApiBindProductCombo.getUserProductComboId());
                if (userProductCombo != null) {
                    tradePlatformApiBindProductCombo.setUserProductCombo(userProductCombo);
                }
            }
        }

       // tradePlatformApiBindProductComboPageInfo.setList(pageList);

        return tradePlatformApiBindProductComboPageInfo;
    }


    @Override
    public Optional<Integer> add(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {
        return Optional.ofNullable(tradePlatformApiBindProductComboMapper.insertSelective(tradePlatformApiBindProductCombo));
    }


    /**
     * 查询没有绑定的交易平台api列表 通过用户ID
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<TradePlatformApi> findNoBindTradePlatformApiListByUserId(int userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo(this.tradePlatformApiBindProductComboMapper.findNotInBindAndInApiListByUserId(userId));
    }

    /**
     * 查询没有绑定的用户套餐 通过用户ID
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<UserProductCombo> findNoBindUserProductComboListByUserId(int userId, int pageNum, int pageSize) {

        //分页查询用户为绑定的用户套餐
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Integer> pageInfo = new PageInfo<>(this.tradePlatformApiBindProductComboMapper.findNotInBindAndInComboIdsByUserId(userId));
        List<Integer> ids = pageInfo.getList();
        //用来保存用户套餐列表
        List<UserProductCombo> userProductComboList=new ArrayList<>();
        //根据用户套餐ID 调用套餐服务获取套餐信息
        for (Integer id : ids) {
            UserProductCombo userProductCombo = this.iUserProductComboClientService.getUserProductCombo(id);
            userProductComboList.add(userProductCombo);
        }

        return new PageInfo<>(userProductComboList);
    }

    @Override
    public Optional<Integer> checkExistByUserIdAndTradePlatformApiId(int userId, int tradePlatformApiId) {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> checkExistByUserIdAndUserProductComboId(int userId, int userProductComboId) {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> updateBindApi(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {
        return Optional.ofNullable(this.tradePlatformApiBindProductComboMapper.updateApiByPrimaryKey(tradePlatformApiBindProductCombo));
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.ofNullable(this.tradePlatformApiBindProductComboMapper.deleteByPrimaryKey(id));
    }
}
