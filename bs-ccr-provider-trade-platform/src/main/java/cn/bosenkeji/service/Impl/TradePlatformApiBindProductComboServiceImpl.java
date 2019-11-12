package cn.bosenkeji.service.Impl;

import cn.bosenkeji.interfaces.CommonResultNumberEnum;
import cn.bosenkeji.interfaces.TradePlatformApiBoundStatus;
import cn.bosenkeji.mapper.TradePlatformApiBindProductComboMapper;
import cn.bosenkeji.mapper.TradePlatformApiMapper;
import cn.bosenkeji.service.IUserProductComboClientService;
import cn.bosenkeji.service.TradePlatformApiBindProductComboService;
import cn.bosenkeji.service.TradePlatformApiService;
import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductCombo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    @Resource
    private TradePlatformApiService tradePlatformApiService;

    //注入用户套餐生产者
    @Resource
    private IUserProductComboClientService iUserProductComboClientService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public PageInfo<TradePlatformApiBindProductCombo> findByUserIdWithPage(int userId, int pageNum, int pageSize) {

        //查询用户下的
        PageHelper.startPage(pageNum,pageSize);

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


    //@Hmily(confirmMethod = "addConfirm",cancelMethod = "addCancel")
    @Transactional
    @Override
    public int add(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {

        //int i=1/0;
        int result=tradePlatformApiBindProductComboMapper.insertSelective(tradePlatformApiBindProductCombo);
        //int i=1/0;
        return result;
    }

    public int addConfirm(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {

        logger.info(tradePlatformApiBindProductCombo.getUserProductComboId()+"部署绑定机器人成功");
        return 2;
    }

    public int addCancel(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {

        logger.error(tradePlatformApiBindProductCombo.getUserProductComboId()+"部署绑定机器人失败，进行cancel操作");
        tradePlatformApiBindProductComboMapper.deleteByPrimaryKey(tradePlatformApiBindProductCombo.getId());
        return -2;
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


    @Transactional
    @Override
    public int updateBindApi(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {
        try {
            //把绑定前的 api状态设为 未绑定
            TradePlatformApiBindProductCombo preBinding = tradePlatformApiBindProductComboMapper.selectByPrimaryKey(tradePlatformApiBindProductCombo.getId());
            if (null == preBinding)
                return CommonResultNumberEnum.FAIL;

            //如果已绑定 则把前api 绑定状态 设为未绑定
            if(preBinding.getTradePlatformApiId() > 0)
                tradePlatformApiService.updateIsBound(preBinding.getTradePlatformApiId(), TradePlatformApiBoundStatus.NOT_EXIST_BOUND);

            //更新 绑定
            int result = this.tradePlatformApiBindProductComboMapper.updateApiByPrimaryKey(tradePlatformApiBindProductCombo);

            //把绑定后的api 壮体啊设为 已绑定
            if (result > 0) {
                if (tradePlatformApiBindProductCombo.getTradePlatformApiId()>0) {
                    tradePlatformApiService.updateIsBound(tradePlatformApiBindProductCombo.getTradePlatformApiId(), TradePlatformApiBoundStatus.EXIST_BOUND);
                }
            }

            return result;
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return CommonResultNumberEnum.FAIL;
        }

    }

    @Transactional
    @Override
    public int delete(int id) {

        try{

            //api 绑定状态 设置未 绑定
            TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo = tradePlatformApiBindProductComboMapper.selectByPrimaryKey(id);
            if (null != tradePlatformApiBindProductCombo && tradePlatformApiBindProductCombo.getTradePlatformApiId() > 0) {
                tradePlatformApiService.updateIsBound(tradePlatformApiBindProductCombo.getTradePlatformApiId(), TradePlatformApiBoundStatus.NOT_EXIST_BOUND);
            }

            //删除
            int result = this.tradePlatformApiBindProductComboMapper.deleteByPrimaryKey(id);

            return result;

            }catch (Exception e) {
                e.printStackTrace();
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return CommonResultNumberEnum.FAIL;
        }
    }

    @Override
    public int checkExistByUserIdAndId(int userId, int id) {
        return this.tradePlatformApiBindProductComboMapper.checkExistByUserIdAndId(userId,id);
    }

    @Transactional
    @Override
    public int removeBinding(int id) {

        try {

            // 先修改api  的 isBound 状态
            TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo = tradePlatformApiBindProductComboMapper.selectByPrimaryKey(id);
            if (null != tradePlatformApiBindProductCombo && tradePlatformApiBindProductCombo.getTradePlatformApiId() > 0) {
                tradePlatformApiService.updateIsBound(tradePlatformApiBindProductCombo.getTradePlatformApiId(), TradePlatformApiBoundStatus.NOT_EXIST_BOUND);
            }

            //解除 绑定
            int result = tradePlatformApiBindProductComboMapper.updateApiIdByPrimaryKey(id, 0);

            return result;

        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return CommonResultNumberEnum.FAIL;
        }
    }

    @Override
    public TradePlatformApiBindProductCombo getByUserIdAndComboId(int userId, int userProductComboId) {
        return tradePlatformApiBindProductComboMapper.getByUserIdAndComboId(userId,userProductComboId);
    }

    @Override
    public int deleteByComboId(int userProductComboId) {

        //先 把 api绑定状态 设为未绑定 再删除
        TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo = tradePlatformApiBindProductComboMapper.selectByComboId(userProductComboId);
        if(null != tradePlatformApiBindProductCombo && tradePlatformApiBindProductCombo.getTradePlatformApiId() > 0) {
            tradePlatformApiService.updateIsBound(tradePlatformApiBindProductCombo.getId(),TradePlatformApiBoundStatus.NOT_EXIST_BOUND);
        }

        return tradePlatformApiBindProductComboMapper.deleteByComboId(userProductComboId);
    }

    @Override
    public int checkExistByComboId(int userProductComboId) {
        return this.tradePlatformApiBindProductComboMapper.checkExistByComboId(userProductComboId);
    }

    @Override
    public List findAll() {
        return tradePlatformApiBindProductComboMapper.findAll();
    }

    @Override
    public int getUserIdById(int id) {
        return 0;
    }
}
