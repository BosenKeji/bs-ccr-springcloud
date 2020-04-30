package cn.bosenkeji.service.Impl;

import cn.bosenkeji.UserComboRedisEnum;
import cn.bosenkeji.config.RobotRunStatusSource;
import cn.bosenkeji.interfaces.CommonStatusEnum;
import cn.bosenkeji.mapper.ProductComboMapper;
import cn.bosenkeji.mapper.UserProductComboMapper;
import cn.bosenkeji.service.*;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.utils.RocketMqUtil;
import cn.bosenkeji.utils.UserComboTimeUtil;
import cn.bosenkeji.vo.ComboStatusEnum;
import cn.bosenkeji.vo.RobotRunStatusParams;
import cn.bosenkeji.vo.User;
import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import cn.bosenkeji.vo.product.Product;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductCombo;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import scala.Int;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service.Impl
 * @Version V1.0
 * @create 2019-07-15 11:17
 */
@Service
public class UserProductComboServiceImpl implements IUserProductComboService {

    @Resource
    private UserProductComboMapper userProductComboMapper;
    @Resource
    private ProductComboMapper productComboMapper;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private IUserClientService iUserClientService;
    @Resource
    private IProductClientService iProductClientService;
    @Autowired
    private JobService jobService;
    @Resource
    private RobotRunStatusSource robotRunStatusSource;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final int FAIL=0;
    private final int SUCCESS=1;

    @Resource
    private ITradePlatformApiBindProductComboClientService iTradePlatformApiBindProductComboClientService;

    /**
     * 添加用户套餐接口
     * @param userProductCombo
     * @return
     */
    @Transactional
    @Override
    public int add(UserProductCombo userProductCombo) {
        //查询套餐时长
        Integer time=productComboMapper.selectTimeByPrimaryKey(userProductCombo.getProductComboId());

        //保存到数据库 管理端部署机器人
        int adminResult=userProductComboMapper.insertSelective(userProductCombo);
        //部署失败直接返回
        if(adminResult!=SUCCESS)
            return FAIL;
        //把套餐剩余时长 存进缓存中
        UserComboTimeUtil.saveComboTimeToRedis(time,redisTemplate,userProductCombo,jobService);

        return userProductCombo.getId();
    }

    @Override
    public int update(UserProductCombo userProductCombo) {
        return userProductComboMapper.updateByPrimaryKeySelective(userProductCombo);
    }

    @Override
    public PageInfo<UserProductCombo> list(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<UserProductCombo> all = userProductComboMapper.findAll();
        getProductByPids(all);
        return new PageInfo<UserProductCombo>(all);
    }

    /**
     * 删除用户套餐功能，同时删除绑定记录，还有 redis的剩余时长
     * @param id
     * @return
     */
    @Override
    public Result delete(int id) {

        Result result = iTradePlatformApiBindProductComboClientService.deleteByComboId(id);
        int result1=(int) result.getData();
        int result2 = userProductComboMapper.deleteByPrimaryKey(id);
        String redisKey = (String) redisTemplate.opsForHash().get(UserComboRedisEnum.ComboRedisKey, String.valueOf(id));
        if(redisKey!=null) {
            redisTemplate.opsForZSet().remove(redisKey,String.valueOf(id));
            redisTemplate.opsForHash().delete(UserComboRedisEnum.ComboRedisKey,String.valueOf(id));
        }

        System.out.println("result1:"+result1);
        System.out.println("result2:"+result2);
        if(result1>0||result2>0) {
            return new Result<>(1,"删除操作成功，ID为"+id+"的 userProductCombo 删除个数为"+result1+"。同时删除api绑定机器人 的个数 为"+result2);
        }
        else {
            return new Result(0,"删除操作成功，ID为"+id+"的 userProductCombo 删除个数为"+result1+"。同时删除api绑定机器人 的个数 为"+result2);
        }
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        return 0;
    }

    @Override
    public int checkExistByIdAndUserId(int id,int userId) {
        int result = this.userProductComboMapper.checkExistByIdAndUserId(id,userId);
        return result;
    }

    //多表查询
    @Override
    public UserProductCombo get(int id) {

        //数据库联表查询用户套餐信息
        UserProductCombo userProductCombo = userProductComboMapper.selectByPrimaryKey(id);
        //根据id获取用户套餐的有效时长
        if(userProductCombo!=null)
            setTimeForOneCombo(userProductCombo);
        return userProductCombo;


    }

    @Override
    public int checkExistByProductComboId(int productComboId) {
        return userProductComboMapper.checkExistByProductComboId(productComboId);
    }

    @Override
    public int checkExistById(Integer id) {
        return userProductComboMapper.checkExistById(id);
    }

    /**
     * 联合查询用户套餐时长列表
     * @param pageNum
     * @param pageSize
     * @param userTel
     * @return
     */
    @Override
    public PageInfo<UserProductCombo> selectUserProductComboByUserTel(int pageNum,int pageSize,String userTel) {


        User user=null;
        try{
            user = iUserClientService.getOneUserByTel(userTel);
        }catch (Exception e) {
            e.printStackTrace();
            return new PageInfo<>();
        }

        //查不到用户
        if(user==null) {
            return new PageInfo<>();
        }
        //从数据库查询
        return selectUserProductComboByUserId(pageNum,pageSize,user.getId());

    }

    @Override
    public PageInfo<UserProductCombo> selectUserProductComboByUserTelAndId(int pageNum, int pageSize, String tel, Integer id) {

        if (StringUtils.isEmpty(tel) && (id == null || id <=0) ) {
            return new PageInfo<>();
        }

        if (StringUtils.isEmpty(tel)) {
            return selectUserProductComboByUserIdAndId(pageNum,pageSize,null,id);
        }

        User user=null;

        try{
            user = iUserClientService.getOneUserByTel(tel);
        }catch (Exception e) {
            e.printStackTrace();
            return new PageInfo<>();
        }

        //查不到用户
        if(user==null) {
            return new PageInfo<>();
        }
        //从数据库查询

        return selectUserProductComboByUserIdAndId(pageNum,pageSize,user.getId(),id);
    }

    /**
     * 联合查询用户套餐时长列表
     * @param pageNum
     * @param pageSize
     * @param userId 用户ID
     * @return
     */
    @Override
    public PageInfo<UserProductCombo> selectUserProductComboByUserId(int pageNum,int pageSize,int userId) {

        //从数据库查询
        PageHelper.startPage(pageNum,pageSize);

        List<UserProductCombo> userProductCombos = userProductComboMapper.selectUserProductComboByUserId(userId);

        getProductByPids(userProductCombos);

        return new PageInfo<>(userProductCombos);
    }

    /**
     * 根据
     * @param pageNum
     * @param pageSize
     * @param userId 用户ID
     * @return
     */
    public PageInfo<UserProductCombo> selectUserProductComboByUserIdAndId(int pageNum,int pageSize,Integer userId, Integer id) {

        //从数据库查询
        PageHelper.startPage(pageNum,pageSize);
        List<UserProductCombo> userProductCombos = userProductComboMapper.selectUserProductComboByIdAndUserId(id,userId);
        getProductByPids(userProductCombos);

        return new PageInfo<>(userProductCombos);
    }

    /**
     * 检查用户 是否买过 相同产品 方法
     * @param productComboId
     * @param userId
     * @return
     */
    @Override
    public int checkExistByProductIdAndUserId(int productComboId,int userId) {
        int productId=productComboMapper.selectProductIdByPrimaryKey(productComboId);
        return userProductComboMapper.checkExistByProductIdAndUserId(productId,userId);
    }

    @Override
    public Map<Integer,UserProductCombo> selectByPrimaryKeys(List<Integer> ids) {
        Map<Integer,UserProductCombo> userProductCombosMap=userProductComboMapper.selectByPrimaryKeys(ids);
        List<UserProductCombo> userProductCombos=new ArrayList<>(userProductCombosMap.values());
        getProductByPids(userProductCombos);
        return userProductCombosMap;
    }

    /**
     *  往userProductCombo  填充 剩余时长 和 product 方法
     * @param userProductCombos  需要填充的用户套餐列表
     * @return
     */
    public List<UserProductCombo> getProductByPids(List<UserProductCombo> userProductCombos) {

        //逐一设置套餐时间 并 收集id列表
        List<Integer> pids=new ArrayList<>();
        List<Integer> userIds=new ArrayList<>();

        for (UserProductCombo userProductCombo : userProductCombos) {

            // 设置套餐时间
            setTimeForOneCombo(userProductCombo);
            //循环收集产品ID
            pids.add(userProductCombo.getProductCombo().getProductId());
            //收集用户id
            userIds.add(userProductCombo.getUserId());
        }

        //填充product 信息
        if(pids!=null&&pids.size()>0) {

            //通过多个id获取产品的 map
            Map<Integer, Product> productMap=iProductClientService.listByPrimaryKeys(pids);
            Map<Integer, User> userMap = iUserClientService.listByIds(userIds);
            //循环把产品 用户信息 映射到套餐中
            for (UserProductCombo userProductCombo:userProductCombos) {
                Integer id=userProductCombo.getProductCombo().getProductId();
                int userId = userProductCombo.getUserId();
                if(id > 0 && productMap.containsKey(id)) {
                    userProductCombo.getProductCombo().setProduct(productMap.get(id));
                }
                if (userId >0 && userMap.containsKey(userId) ) {
                    userProductCombo.setUser(userMap.get(userId));
                }
            }
        }
        return userProductCombos;
    }



    /**
     *  刷新全部套餐剩余时长
     * @return
     */
    @Override
    public int flushAllComboDay() {

        System.out.println("用户时长 数据刷新中……");
        List<UserProductCombo> all = userProductComboMapper.findAllWithDay();

        //清空redis，否则会叠加很多zset
        Set keys = redisTemplate.keys(UserComboRedisEnum.UserComboTime + "*");
        redisTemplate.delete(keys);

        int result = countComboTypeByCombo(all,null);
        System.out.println("用户时长 数据刷新完成！！！！！");
        return result;
    }

    /**
     *  初始化 刷新用户套餐剩余时长
     *  不同强制刷新是 此方法只在 comboRedisKey 不存在时才会刷新数据
     * @return
     */
    @Override
    public int initFlushAllComboDay() {

        if(redisTemplate.hasKey(UserComboRedisEnum.ComboRedisKey))
            return FAIL;
        System.out.println("用户时长 数据刷新中……");
        List<UserProductCombo> all = userProductComboMapper.findAllWithDay();

        //清空redis，否则会叠加很多zset
        Set keys = redisTemplate.keys(UserComboRedisEnum.UserComboTime + "*");
        redisTemplate.delete(keys);

        int result = countComboTypeByCombo(all,null);
        System.out.println("用户时长 数据刷新完成！！！！！");
        return result;
    }

    /**
     *  通过一个或多个 ID 刷新用户套餐 剩余时长
     * @param ids
     * @return
     */
    @Override
    public int flushSomeComboDay(List<Integer> ids) {

        List<UserProductCombo> someCombo = userProductComboMapper.selectByPrimaryKeysWithDay(ids);

        List<String> keys=new ArrayList();
        for (UserProductCombo userProductCombo : someCombo) {
            keys.add(String.valueOf(userProductCombo.getId()));
        }
        redisTemplate.delete(keys);
        int result = countComboTypeByCombo(someCombo,ids);
        return result;
    }

    /**
     * 输入一个或多个用户套餐，计算剩余时长并保存在 redis
     * 参数 ids 因为还没引入批量操作，暂时未用到
     * @param all
     * @return
     */
    public int countComboTypeByCombo(List<UserProductCombo> all,List<Integer> ids) {

        int result=0;
        long currentTime = Timestamp.valueOf(LocalDateTime.now()).getTime();

        if(all!=null&&all.size()>0) {
            for (UserProductCombo userProductCombo : all) {

                //读取增补时长 把增补的时长累加起来
                int addNumber=0;
                List<UserProductComboDay> userProductComboDays = userProductCombo.getUserProductComboDays();
                for (UserProductComboDay userProductComboDay : userProductComboDays) {
                    int number = userProductComboDay.getNumber();
                    if(number>0) {
                        addNumber+=number;
                    }
                }

                //获取创建时间
                Timestamp createdAt = userProductCombo.getCreatedAt();
                long createTime = createdAt.getTime();

                //已经使用了的时长
                int remain=(int) ((currentTime-createTime)/(1000*60*60*24));

                //套餐时长+增补时长 减去 用去的时长  =  用户套餐剩余时间
                Integer remainTime= userProductCombo.getProductCombo().getTime() + addNumber - remain;
                if(remainTime>0) {

                    UserComboTimeUtil.saveComboTimeToRedis(remainTime,redisTemplate,userProductCombo,jobService);
                    result++;
                }
            }


        }
        return result;
    }


    /**
     * 传入一个userProductCombo 从redis中查询剩余时间并设置进来
     * @param userProductCombo
     */
    public void setTimeForOneCombo(UserProductCombo userProductCombo) {
        //从缓存拿去剩余时间 把剩余时长填充到userProductCombo中
        int id = userProductCombo.getId();
        //先从 hash 中获取 对应用户套餐 保存时长的 zset位置
        String redisKey = (String) redisTemplate.opsForHash().get(UserComboRedisEnum.ComboRedisKey, String.valueOf(id));
        //再获取时长
        Double score=0.0;
        if(redisKey!=null)
            score = redisTemplate.opsForZSet().score(redisKey, String.valueOf(id));
        int time=0;

        //注意判空处理
        if(score!=null)
            time=score.intValue();

        //设置剩余时间
        userProductCombo.setRemainTime(time>0?time:0);
    }

    int getOneComboTime(int id) {
        String redisKey = (String) redisTemplate.opsForHash().get(UserComboRedisEnum.ComboRedisKey, String.valueOf(id));
        Double score=0.0;
        if(redisKey!=null)
            score = redisTemplate.opsForZSet().score(redisKey, String.valueOf(id));
        int time=0;

        //注意判空处理
        if(score!=null)
            time=score.intValue();
        return time;
    }

    /**
     * 1 未管控 2暂停 3封禁
     * @param id
     * @param runStatus
     * @return
     */
    @Override
    @Transactional
    public Result<Integer> updateRunStatus(int id, int runStatus) {
        try {


            if (runStatus < 1) {
                return new Result<>(0, "运行状态不存在！");
            }
            UserProductCombo userProductCombo = userProductComboMapper.selectByPrimaryKey(id);
            if (null == userProductCombo) {
                return new Result<>(0, "机器人不存在");
            }
            if (userProductCombo.getRunStatus() == runStatus) {
                return new Result<>(0,"未检测到更新");
            }
            if (userProductCombo.getRunStatus() == 3) {
                return new Result<>(0, "机器人已被封禁");
            }
            int update = userProductComboMapper.updateRunStatus(id, runStatus);
            if (update != 1) {
                return new Result<>(0, "找不到机器人");
            }

            //处理redis中间件数据
            if (runStatus == 1) {
                handleRerun(id);
            } else if (runStatus == 2) {
                handleStopRobot(id);
            } else if (runStatus == 3) {
                handleFreezeRobot(id);
            }

            return new Result<>(CommonStatusEnum.NORMAL);
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result<>(0,"程序发生未知异常");
        }
    }


    /**
     * 处理过期 机器人方法
     * 从倒数的redis set中去除 相关的机器人
     * 将过期的机器人添加到 不运行状态的redis set里面
     * 发送到 全球路由 通知其他区域 这批机器人 不能参与交易了
     * @param robotIds
     */
    @Override
    public void handleExpiredRobot(List<Integer> robotIds) {

        addToNotRunSet(robotIds);
        removeRedisDay(robotIds);
        RobotRunStatusParams robotRunStatusParams = new RobotRunStatusParams(ComboStatusEnum.TO_NOT_RUN, robotIds);
        sendRobotRunStatusMsg(robotRunStatusParams);

    }

    /**
     * 处理冻结的机器人
     * 1 把倒数的redis 移到 静止的redis
     * 2 把 机器人加到 不运行 redis
     * 3 全球路由广播
     * @param robotId
     */
    public void handleFreezeRobot(int robotId) {

        List<Integer> list = new ArrayList<>();
        list.add(robotId);
        redisTemplate.opsForSet().add(ComboStatusEnum.NOT_RUN_ROBOT_ID_SET, String.valueOf(robotId));
        int time = getOneComboTime(robotId);
        redisTemplate.opsForZSet().add(ComboStatusEnum.STOP_COUNT_DOWN_ROBOT, String.valueOf(robotId), time);
        removeRedisDay(list);
        RobotRunStatusParams robotRunStatusParams = new RobotRunStatusParams(ComboStatusEnum.TO_NOT_RUN, list);
        sendRobotRunStatusMsg(robotRunStatusParams);

    }

    /**
     * 处理暂停运行的机器人
     * 1 把倒数的redis 移到 冻结的的redis
     * 2 把 机器人加到 不运行 redis
     * 3 全球路由广播
     * @param robotId
     */
    public void handleStopRobot(int robotId) {

        List<Integer> list = new ArrayList<>();
        list.add(robotId);
        redisTemplate.opsForSet().add(ComboStatusEnum.NOT_RUN_ROBOT_ID_SET, String.valueOf(robotId));
        int time = getOneComboTime(robotId);
        redisTemplate.opsForZSet().add(ComboStatusEnum.STOP_COUNT_DOWN_ROBOT, String.valueOf(robotId), time);
        removeRedisDay(list);
        RobotRunStatusParams robotRunStatusParams = new RobotRunStatusParams(ComboStatusEnum.TO_NOT_RUN, list);
        sendRobotRunStatusMsg(robotRunStatusParams);

    }

    /**
     * 处理 重新运行的机器人
     * 1 把静止的redis 移到 倒数的redis
     * 2 把机器人从 不运行状态中 移除
     * 3 全球路由广播 该机器人可以交易
     * @param robotId
     */
    public void handleRerun(int robotId) {

        Double score = redisTemplate.opsForZSet().score(ComboStatusEnum.STOP_COUNT_DOWN_ROBOT, String.valueOf(robotId));
        if (null == score || score < 1) {
            return;
        }
        List<Integer> list = new ArrayList<>();
        list.add(robotId);
        removeFormNotRunSet(list);

        RobotRunStatusParams robotRunStatusParams = new RobotRunStatusParams(ComboStatusEnum.TO_RUN, list);
        sendRobotRunStatusMsg(robotRunStatusParams);

        UserProductCombo userProductCombo = new UserProductCombo();
        userProductCombo.setId(robotId);
        UserComboTimeUtil.saveComboTimeToRedis(score.intValue(),redisTemplate,userProductCombo,jobService);

        removeFormStopCountDownSet(list);
    }

    void removeRedisDay(List<Integer> robotIds) {

           // logger.info(robotIds.toArray().toString());
            System.out.println("robotIds = " + robotIds);
            List<String> strIds = robotIds.stream().map(String::valueOf).collect(Collectors.toList());
            String redisKey = (String) redisTemplate.opsForHash().get(UserComboRedisEnum.ComboRedisKey, String.valueOf(robotIds.get(0)));
            if (redisKey != null) {
                Long remove = redisTemplate.opsForZSet().remove(redisKey, strIds.toArray());
                System.out.println("remove = " + remove);
                Long delete = redisTemplate.opsForHash().delete(UserComboRedisEnum.ComboRedisKey, strIds.toArray());
                System.out.println("delete = " + delete);
            }
    }

    /**
     * 添加到国内 不运行 set集合
     * @param robotIds
     * @return
     */
    int addToNotRunSet(List<Integer> robotIds) {
        Long add = redisTemplate.opsForSet().add(ComboStatusEnum.NOT_RUN_ROBOT_ID_SET, robotIds.toArray());
        return add.intValue();
    }

    int addToStopCountDownSet(int robotIds, int time) {
        Boolean add = redisTemplate.opsForZSet().add(ComboStatusEnum.STOP_COUNT_DOWN_ROBOT, robotIds, time);
        return add ? 1:0;
    }

    int removeFormNotRunSet(List<Integer> robotIds) {
        List<String> strIds = robotIds.stream().map(String::valueOf).collect(Collectors.toList());
        Long add = redisTemplate.opsForSet().remove(ComboStatusEnum.NOT_RUN_ROBOT_ID_SET, strIds.toArray());
        return add.intValue();
    }

    int removeFormStopCountDownSet(List<Integer> robotIds) {
        List<String> strIds = robotIds.stream().map(String::valueOf).collect(Collectors.toList());
        Long remove = redisTemplate.opsForZSet().remove(ComboStatusEnum.STOP_COUNT_DOWN_ROBOT, strIds.toArray());
        return remove.intValue();
    }


    /**
     * 处理不运行的机器人
     * 发送到 全球路由mq
     * @param robotRunStatusParams
     */
    private void sendRobotRunStatusMsg(RobotRunStatusParams robotRunStatusParams) {

        if (robotRunStatusParams.getRobotIds() != null && robotRunStatusParams.getRobotIds().size() > 0) {
            logger.info("====== type: {} |||  robotIds : {}",robotRunStatusParams.getType(),robotRunStatusParams.getRobotIds());
            boolean send = robotRunStatusSource.robotRunStatusOutput().send(RocketMqUtil.generateRobotRunStatusMessage(robotRunStatusParams));
            if (!send) {
                throw new RuntimeException("======== send rocket mq fail==========");
            }
        }

    }

    public static void main(String[] args) {
        List<Integer> robotIds = new ArrayList<>();
        robotIds.add(1);
        robotIds.add(2);
        List<String> strIds = robotIds.stream().map(String::valueOf).collect(Collectors.toList());
        List<String> result = new ArrayList<>();
        robotIds.forEach(id -> {
            result.add(String.valueOf(id));
        });
        result.add("hello");
        System.out.println("result = " + result);
        System.out.println("strIds = " + strIds);
    }
}
