package cn.bosenkeji.service.Impl;

import cn.bosenkeji.interfaces.CommonResultNumberEnum;
import cn.bosenkeji.interfaces.CommonStatusEnum;
import cn.bosenkeji.interfaces.TradePlatformApiBoundStatus;
import cn.bosenkeji.mapper.TradePlatformApiBindProductComboMapper;
import cn.bosenkeji.mapper.TradePlatformApiMapper;
import cn.bosenkeji.service.IUserProductComboClientService;
import cn.bosenkeji.service.TradePlatformApiBindProductComboService;
import cn.bosenkeji.service.TradePlatformApiService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.combo.UserProductComboVO;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductCombo;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductComboNoComboVo;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductComboVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

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



    @Transactional
    @Override
    public int add(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {


        tradePlatformApiBindProductCombo.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        tradePlatformApiBindProductCombo.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        tradePlatformApiBindProductCombo.setStatus(1);
        int result=tradePlatformApiBindProductComboMapper.insertSelective(tradePlatformApiBindProductCombo);

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
     * 根据userId查询 机器人列表方法
     * 先查询 userProductCombo
     * 再查询 绑定的 tradePlatformApi
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<TradePlatformApiBindProductComboVo> findBindUserProductComboByUserId(int userId, int pageNum, int pageSize) {
        PageInfo pageInfo = iUserProductComboClientService.listByUserId(userId, pageNum, pageSize);
        List<UserProductComboVO> userProductCombos = pageInfo.getList();

        Set<Integer> userProductComboIds = new HashSet<>();
        userProductCombos.forEach(userProductCombo -> {
            userProductComboIds.add(userProductCombo.getId());
        });

        if(userProductComboIds.size() < 1)
            return new PageInfo<>();
        Map<Integer, TradePlatformApiBindProductCombo> tradePlatformApiBindProductComboMap = tradePlatformApiBindProductComboMapper.selectByComboIdsForMap(userProductComboIds);

        userProductCombos.forEach(userProductCombo -> {
            if(tradePlatformApiBindProductComboMap.containsKey(userProductCombo.getId())) {
                userProductCombo.setTradePlatformApiBindProductCombo(tradePlatformApiBindProductComboMap.get(userProductCombo.getId()));
            }
        });

        List<TradePlatformApiBindProductComboVo> result = new ArrayList<>();
        userProductCombos.forEach(userProductCombo -> {
            TradePlatformApiBindProductComboVo tradePlatformApiBindProductComboVo = new TradePlatformApiBindProductComboVo();

            tradePlatformApiBindProductComboVo.setUserProductComboId(userProductCombo.getId());
            tradePlatformApiBindProductComboVo.setUserId(userProductCombo.getUserId());
            tradePlatformApiBindProductComboVo.setRemainTime(userProductCombo.getRemainTime());
            final TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo = userProductCombo.getTradePlatformApiBindProductCombo();
            //填充 tradePlatformApi相关信息
            if(tradePlatformApiBindProductCombo != null) {
                tradePlatformApiBindProductComboVo.setTradePlatformApiBindProductComboId(tradePlatformApiBindProductCombo.getId());
                if(tradePlatformApiBindProductCombo.getTradePlatformApi() != null) {
                    tradePlatformApiBindProductComboVo.setTradePlatformApiId(tradePlatformApiBindProductCombo.getTradePlatformApiId());
                    tradePlatformApiBindProductComboVo.setTradePlatformApiNickname(tradePlatformApiBindProductCombo.getTradePlatformApi().getNickname());
                    tradePlatformApiBindProductComboVo.setTradePlatformApiSign(tradePlatformApiBindProductCombo.getTradePlatformApi().getSign());
                    tradePlatformApiBindProductComboVo.setTradePlatformApiSecret(tradePlatformApiBindProductCombo.getTradePlatformApi().getSecret());

                    if(tradePlatformApiBindProductCombo.getTradePlatformApi().getTradePlatform() != null) {
                        tradePlatformApiBindProductComboVo.setTradePlatformId(tradePlatformApiBindProductCombo.getTradePlatformApi().getTradePlatformId());
                        tradePlatformApiBindProductComboVo.setTradePlatformName(tradePlatformApiBindProductCombo.getTradePlatformApi().getTradePlatform().getName());
                        tradePlatformApiBindProductComboVo.setTradePlatformLogo(tradePlatformApiBindProductCombo.getTradePlatformApi().getTradePlatform().getLogo());
                    }
                }
            }

            //填充product|combo 相关信息

            tradePlatformApiBindProductComboVo.setProductComboId(userProductCombo.getProductComboId());
            tradePlatformApiBindProductComboVo.setProductComboName(userProductCombo.getProductComboName());

            tradePlatformApiBindProductComboVo.setProductId(userProductCombo.getProductId());
            tradePlatformApiBindProductComboVo.setProductName(userProductCombo.getProductName());
            tradePlatformApiBindProductComboVo.setProductLogo(userProductCombo.getProductLogo());
            tradePlatformApiBindProductComboVo.setProductVersionName(userProductCombo.getProductVersionName());
            result.add(tradePlatformApiBindProductComboVo);

        });


        return new PageInfo<>(result);
    }

    /**
     * 判断原来是否有绑定
     * 有绑定则把原来的绑定 逻辑删除
     * 绑定： 新增b绑定记录
     *       把 逻辑删除的记录 更新为1 未删除
     * @param userProductComboId
     * @param tradePlatformApiId
     * @return
     */
    @Transactional
    @Override
    public Result<Integer> apiBindCombo(int userId,int userProductComboId, int tradePlatformApiId) {

        try{
            //判断用户操作是否合法
            Result<Integer> checkExistByUserId = iUserProductComboClientService.checkExistByIdAndUserId(userProductComboId,userId);
            if(checkExistByUserId.getData() < 1)
                return new Result<>(CommonResultNumberEnum.FAIL,"用户套餐不存在！");
            if(tradePlatformApiMapper.checkExistByIdAndUserId(tradePlatformApiId,userId) <1) {
                return new Result<>(CommonResultNumberEnum.FAIL,"用户 交易平台api不存在");
            }
            TradePlatformApiBindProductCombo byTradePlatformApiId = tradePlatformApiBindProductComboMapper.selectByComboAndApi(null, tradePlatformApiId, CommonStatusEnum.NORMAL);
            if (byTradePlatformApiId !=null && byTradePlatformApiId.getUserProductComboId() != userProductComboId) {
                return new Result<>(CommonResultNumberEnum.FAIL,"此api 已绑定其他机器人");
            }

            //原来的绑定
            TradePlatformApiBindProductCombo oldBind = tradePlatformApiBindProductComboMapper.selectByComboAndApi(userProductComboId, null, CommonStatusEnum.NORMAL);

            //查询当前绑定记录
            TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo = tradePlatformApiBindProductComboMapper.selectByComboAndApi(userProductComboId, tradePlatformApiId, null);

            if(tradePlatformApiBindProductCombo == null) {
                //创建新的 绑定记录
                TradePlatformApiBindProductCombo newTradePlatformApiBindCombo = new TradePlatformApiBindProductCombo();
                newTradePlatformApiBindCombo.setUserProductComboId(userProductComboId);
                newTradePlatformApiBindCombo.setTradePlatformApiId(tradePlatformApiId);
                newTradePlatformApiBindCombo.setUserId(userId);

                int addResult = this.add(newTradePlatformApiBindCombo);
                if(oldBind != null) {
                    //把原来的绑定状态设为0 删除
                    tradePlatformApiBindProductComboMapper.updateStatusById(oldBind.getId(),CommonStatusEnum.DELETE);
                    tradePlatformApiService.updateIsBound(oldBind.getTradePlatformApiId(), TradePlatformApiBoundStatus.NOT_EXIST_BOUND,oldBind.getUserId());

                }
                tradePlatformApiService.updateIsBound(tradePlatformApiId, TradePlatformApiBoundStatus.EXIST_BOUND,userId);

                logger.info("api绑定机器人 新增了 绑定记录！");
                return new Result<>(addResult);

            }else if(tradePlatformApiBindProductCombo.getStatus() == 1) {
                //绑定自己 不用处理
                return new Result<>(CommonResultNumberEnum.FAIL,"当前绑定已存在！");
            }else {
                //更新 把逻辑删除的 绑定记录 状态设为1 逻辑添加
                int result = tradePlatformApiBindProductComboMapper.updateStatusById(tradePlatformApiBindProductCombo.getId(), CommonStatusEnum.NORMAL);
                if(oldBind != null) {
                    //把原来的绑定状态设为0 删除
                    tradePlatformApiBindProductComboMapper.updateStatusById(oldBind.getId(),CommonStatusEnum.DELETE);
                    tradePlatformApiService.updateIsBound(oldBind.getTradePlatformApiId(), TradePlatformApiBoundStatus.NOT_EXIST_BOUND,oldBind.getUserId());

                }
                tradePlatformApiService.updateIsBound(tradePlatformApiBindProductCombo.getTradePlatformApiId(), TradePlatformApiBoundStatus.EXIST_BOUND,tradePlatformApiBindProductCombo.getUserId());
                logger.info("api 绑定 机器人 从逻辑删除中更新为 正常状态！");
                return new Result<>(result);
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result<>(CommonResultNumberEnum.FAIL,e.getMessage());
        }
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
            System.out.println("preBinding = " + preBinding);
            if (null == preBinding)
                return CommonResultNumberEnum.FAIL;

            //更新 绑定
            int result = this.tradePlatformApiBindProductComboMapper.updateApiByPrimaryKey(tradePlatformApiBindProductCombo);

            if (result > 0) {
                //如果已绑定 则把前api 绑定状态 设为未绑定
                if(preBinding.getTradePlatformApiId() > 0)
                    tradePlatformApiService.updateIsBound(preBinding.getTradePlatformApiId(), TradePlatformApiBoundStatus.NOT_EXIST_BOUND,preBinding.getUserId());
                //把绑定后的api 壮体啊设为 已绑定
                if (tradePlatformApiBindProductCombo.getTradePlatformApiId()>0) {
                    tradePlatformApiService.updateIsBound(tradePlatformApiBindProductCombo.getTradePlatformApiId(), TradePlatformApiBoundStatus.EXIST_BOUND,preBinding.getUserId());
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
    public int delete(int id, int userId) {

        try{

            //api 绑定状态 设置未 绑定
            TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo = tradePlatformApiBindProductComboMapper.selectByPrimaryKey(id);
            if (null != tradePlatformApiBindProductCombo && tradePlatformApiBindProductCombo.getTradePlatformApiId() > 0) {
                tradePlatformApiService.updateIsBound(tradePlatformApiBindProductCombo.getTradePlatformApiId(), TradePlatformApiBoundStatus.NOT_EXIST_BOUND,tradePlatformApiBindProductCombo.getUserId());
            }

            //删除
            int result = this.tradePlatformApiBindProductComboMapper.deleteByPrimaryKey(id);
            //result = 0;

            if(result < 1)
                throw new RuntimeException();

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
    public int removeBinding(int id,int userId) {

        try {

            // 先修改api  的 isBound 状态
            TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo = tradePlatformApiBindProductComboMapper.selectByPrimaryKey(id);
            if(tradePlatformApiBindProductCombo == null || tradePlatformApiBindProductCombo.getStatus() == 0) {
                return 0;
            }
            if (tradePlatformApiBindProductCombo.getTradePlatformApiId() > 0) {
                tradePlatformApiService.updateIsBound(tradePlatformApiBindProductCombo.getTradePlatformApiId(), TradePlatformApiBoundStatus.NOT_EXIST_BOUND,tradePlatformApiBindProductCombo.getUserId());
            }

            //解除 绑定
            int result = tradePlatformApiBindProductComboMapper.updateStatusById(id, CommonStatusEnum.DELETE);

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
            tradePlatformApiService.updateIsBound(tradePlatformApiBindProductCombo.getId(),TradePlatformApiBoundStatus.NOT_EXIST_BOUND,tradePlatformApiBindProductCombo.getUserId());
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

    /**
     * 通过 userProductComboIds 查询 已经绑定api的 robot信息，包含 api 信息
     * @param ids
     * @return
     */
    @Override
    public List<TradePlatformApiBindProductComboNoComboVo> listHasBindByUserProductComboIds(Set<Integer> ids) {
        List<TradePlatformApiBindProductCombo> tradePlatformApiBindProductCombos = this.tradePlatformApiBindProductComboMapper.selectByComboIds(ids);
        List<TradePlatformApiBindProductComboNoComboVo> list = new ArrayList<>();
        Iterator<TradePlatformApiBindProductCombo> iterator = tradePlatformApiBindProductCombos.iterator();
        while (iterator.hasNext()) {
            TradePlatformApiBindProductCombo next = iterator.next();

            // 过滤掉没有绑定的 机器人
            if(next.getTradePlatformApi() == null) {
                iterator.remove();
            }
            else if(StringUtils.isBlank(next.getTradePlatformApi().getSign())) {
                iterator.remove();
            }else {
                TradePlatformApiBindProductComboNoComboVo tradePlatformApiBindProductComboVo = new TradePlatformApiBindProductComboNoComboVo();
                tradePlatformApiBindProductComboVo.setApiBindRobotId(next.getId());
                tradePlatformApiBindProductComboVo.setTradePlatformApiId(next.getTradePlatformApiId());
                tradePlatformApiBindProductComboVo.setTradePlatformId(next.getTradePlatformApi().getTradePlatformId());
                tradePlatformApiBindProductComboVo.setSign(next.getTradePlatformApi().getSign());
                tradePlatformApiBindProductComboVo.setUserId(next.getUserId());
                list.add(tradePlatformApiBindProductComboVo);
            }
        }
        return list;
    }
}
