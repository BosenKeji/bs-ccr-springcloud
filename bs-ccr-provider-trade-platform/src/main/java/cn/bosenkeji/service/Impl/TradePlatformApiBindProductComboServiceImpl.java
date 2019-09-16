package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.TradePlatformApiBindProductComboMapper;
import cn.bosenkeji.mapper.TradePlatformApiMapper;
import cn.bosenkeji.service.IUserProductComboClientService;
import cn.bosenkeji.service.TradePlatformApiBindProductComboService;
import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductCombo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service.Impl
 * @Version V1.0
 * @create 2019-07-26 15:47
 */
@Service
public class TradePlatformApiBindProductComboServiceImpl implements TradePlatformApiBindProductComboService {

    @Resource
    private TradePlatformApiBindProductComboMapper tradePlatformApiBindProductComboMapper;

    @Resource
    private TradePlatformApiMapper tradePlatformApiMapper;

    //注入用户套餐生产者
    @Resource
    private IUserProductComboClientService iUserProductComboClientService;

    @Override
    public PageInfo<TradePlatformApiBindProductCombo> findByUserIdWithPage(int userId, int pageNum, int pageSize) {

        //查询用户下的所有的套餐
        //PageInfo pageInfo = this.iUserProductComboClientService.listByUserId(userId, pageNum, pageSize);

        //查询用户下的
        PageHelper.startPage(pageNum,pageSize);
        /*PageInfo<TradePlatformApiBindProductCombo> tradePlatformApiBindProductComboPageInfo =
                new PageInfo<>(tradePlatformApiBindProductComboMapper.findByUserId(userId));*/
        List<TradePlatformApiBindProductCombo> pageList = tradePlatformApiBindProductComboMapper.findByUserId(userId);

        //
        List<Integer> upcIds=new ArrayList<>();

        //分别查询对应的用户套餐——调用用户套餐的服务
        for (TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo : pageList) {
            int upc_id=tradePlatformApiBindProductCombo.getUserProductComboId();
            if(upc_id>0) {
                upcIds.add(upc_id);
                /*UserProductCombo userProductCombo = iUserProductComboClientService.getUserProductCombo(tradePlatformApiBindProductCombo.getUserProductComboId());
                if (userProductCombo != null) {
                    tradePlatformApiBindProductCombo.setUserProductCombo(userProductCombo);
                }*/
            }
        }

        if(upcIds.size()>0) {
            Map<Integer,UserProductCombo> userProductComboMap=iUserProductComboClientService.getByPrimaryKeys(upcIds);
            for (int i=0;i<pageList.size();i++) {
                Integer upc_id=pageList.get(i).getUserProductComboId();
                if(upc_id!=null&&upc_id>0&&userProductComboMap.containsKey(upc_id)) {
                    pageList.get(i).setUserProductCombo(userProductComboMap.get(upc_id));
                }
            }

        }

       // tradePlatformApiBindProductComboPageInfo.setList(pageList);

        return new PageInfo<>(pageList);
    }


    @Override
    public int add(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {

        return tradePlatformApiBindProductComboMapper.insertSelective(tradePlatformApiBindProductCombo);
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
        List<Integer> existApiIds = tradePlatformApiBindProductComboMapper.selectApiIdsByUserId(userId);
        List<TradePlatformApi> userApi = tradePlatformApiMapper.findAllByUserId(userId);
        if(existApiIds!=null&&existApiIds.size()>0) {
            //if(userApi!=null&&userApi.size()>0) {
                for (int i=userApi.size()-1;i>=0;i--) {
                    if(existApiIds.contains(userApi.get(i).getId())) {
                        userApi.remove(i);
                    }
                }
            //}
        }
       // PageInfo<TradePlatformApi> pagei = new PageInfo<>(usetApi);
        return new PageInfo(userApi);
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
        /*PageHelper.startPage(pageNum,pageSize);
        PageInfo<Integer> pageInfo = new PageInfo<>(this.tradePlatformApiBindProductComboMapper.findNotInBindAndInComboIdsByUserId(userId));*/
        //获取
        List<Integer> existComboIds = tradePlatformApiBindProductComboMapper.selectComboIdsByUserId(userId);


        PageInfo pageInfo = iUserProductComboClientService.listByUserId(userId,pageNum,pageSize);
        List<UserProductCombo> userComboList = pageInfo.getList();


        if(existComboIds!=null&&existComboIds.size()>0) {
            if(userComboList!=null&&userComboList.size()>0) {

                for (int i=0;i<userComboList.size()-1;i++) {
                    //userComboIds.add(combo.getId());
                    if(existComboIds.contains(userComboList.get(i).getId())) {
                        userComboList.remove(userComboList.get(i));
                    }

                }

            }
        }

        /*//用来保存用户套餐列表
        List<UserProductCombo> userProductComboList=new ArrayList<>();
        //根据用户套餐ID 调用套餐服务获取套餐信息
        for (Integer id : ids) {
            UserProductCombo userProductCombo = this.iUserProductComboClientService.getUserProductCombo(id);
            userProductComboList.add(userProductCombo);
        }*/

        return new PageInfo<>(userComboList);
    }

    @Override
    public int checkExistByUserIdAndTradePlatformApiId(int userId, int tradePlatformApiId) {

        int i = tradePlatformApiMapper.checkExistByIdAndUserId(tradePlatformApiId, userId);
        if(i>=1) {
            //如果存在就不能插入，即api已经绑定了
            return this.tradePlatformApiBindProductComboMapper.checkExistNotBindApiByUserIdAndTradePlatformApiId(userId,tradePlatformApiId);
        }else {
            //api不存在或者不属于该用户
            return 1;
        }

    }

    /*@Override
    public int checkExistByUserIdAndUserProductComboId(int userId, int userProductComboId) {
        return Optional.ofNullable(this.tradePlatformApiBindProductComboMapper.checkExistNotBindComboByUserIdAndUserProductComboId(userId,userProductComboId));
    }*/

    @Override
    public int updateBindApi(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {
        return this.tradePlatformApiBindProductComboMapper.updateApiByPrimaryKey(tradePlatformApiBindProductCombo);
    }

    @Override
    public int delete(int id) {
        return this.tradePlatformApiBindProductComboMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int checkExistByUserIdAndId(int userId, int id) {
        return this.tradePlatformApiBindProductComboMapper.checkExistByUserIdAndId(userId,id);
    }

    @Override
    public int removeBinding(int id) {

        return tradePlatformApiBindProductComboMapper.updateApiIdByPrimaryKey(id,0);
    }

    @Override
    public TradePlatformApiBindProductCombo getByUserIdAndComboId(int userId, int userProductComboId) {
        return tradePlatformApiBindProductComboMapper.getByUserIdAndComboId(userId,userProductComboId);
    }

    @Override
    public int deleteByComboId(int userProductComboId) {
        return tradePlatformApiBindProductComboMapper.deleteByComboId(userProductComboId);
    }
}
