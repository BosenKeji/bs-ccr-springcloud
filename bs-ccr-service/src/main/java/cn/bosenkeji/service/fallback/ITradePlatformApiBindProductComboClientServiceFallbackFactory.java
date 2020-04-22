package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.ITradePlatformApiBindProductComboClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductCombo;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductComboNoComboVo;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiBindProductComboVo;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service.fallback
 * @Version V1.0
 * @create 2019-07-26 19:48
 */
@Component
public class ITradePlatformApiBindProductComboClientServiceFallbackFactory implements FallbackFactory<ITradePlatformApiBindProductComboClientService> {

    @Override
    public ITradePlatformApiBindProductComboClientService create(Throwable throwable) {
        return new ITradePlatformApiBindProductComboClientService() {
            @Override
            public PageInfo getListByUserId(int pageNum,int pageSize,int userId) {
                TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo=new TradePlatformApiBindProductCombo();
                //tradePlatformApiBindProductCombo.set
                List list=new ArrayList();
                list.add(tradePlatformApiBindProductCombo);
                return new PageInfo(list);
            }

            @Override
            public Result addTradePlatformApiBindProductCombo(TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo) {

                return new Result(0,"hystrix");
            }

            @Override
            public PageInfo getNoBindTradePlatformApiListByUserId(int pageNum, int pageSize, int userId) {
                TradePlatformApi tradePlatformApi=new TradePlatformApi();
                tradePlatformApi.setNickname("hystrix");
                tradePlatformApi.setStatus(0);
                List list=new ArrayList();
                list.add(tradePlatformApi);
                return new PageInfo(list);
            }

            /*@Override
            public PageInfo getNoBindUserProductComboListByUserId(int pageNum, int pageSize, int userId) {

                return new PageInfo();
            }*/

            @Override
            public Result updateTradePlatformApiBindProductCombo(int id, int tradePlatformApiId, int userId) {
                return new Result(0,"hystrix");
            }

            @Override
            public Result deleteTradePlatformApiBindProductCombo(int id, int userId) {
                return new Result(0,"hystrix");
            }

            @Override
            public Result realDelete(int id) {
                return new Result(-1,"hystrix");
            }

            @Override
            public Result deleteByComboId(int userProductComboId) {
                return new Result(-1,"hystrix");
            }

            @Override
            public List findAll() {
                return null;
            }

            @Override
            public int getUserIdById(int id) {
                return 0;
            }

            @Override
            public TradePlatformApiBindProductCombo getOneByPrimaryId(Integer id) {
                TradePlatformApiBindProductCombo tradePlatformApiBindProductCombo = new TradePlatformApiBindProductCombo();
                tradePlatformApiBindProductCombo.setId(0);
                return tradePlatformApiBindProductCombo;
            }

            @Override
            public Result<Integer> apiBindCombo(int userId, int userProductComboId, int tradePlatformApiId) {
                return new Result<>(-1,"hystrix");
            }

            @Override
            public List<TradePlatformApiBindProductComboNoComboVo> listHasBoundByUserProductComboIds(Set<Integer> userProductComboIds) {

                TradePlatformApiBindProductComboNoComboVo tradePlatformApiBindProductComboVo = new TradePlatformApiBindProductComboNoComboVo();
                tradePlatformApiBindProductComboVo.setApiBindRobotId(-1);
                tradePlatformApiBindProductComboVo.setUserId(-1);
                tradePlatformApiBindProductComboVo.setSign("hystrix");
                List<TradePlatformApiBindProductComboNoComboVo> list = new ArrayList<>();
                list.add(tradePlatformApiBindProductComboVo);
                return list;

            }

            @Override
            public PageInfo<TradePlatformApiBindProductComboVo> getProductComboListWithBindByUserId(int pageNum, int pageSize, int userId) {

                TradePlatformApiBindProductComboVo tradePlatformApiBindProductComboVo = new TradePlatformApiBindProductComboVo();
                tradePlatformApiBindProductComboVo.setUserProductComboId(-1);
                tradePlatformApiBindProductComboVo.setRemainTime(-1);
                tradePlatformApiBindProductComboVo.setProductLogo("hystrix");
                List<TradePlatformApiBindProductComboVo> list = new ArrayList<>();
                list.add(tradePlatformApiBindProductComboVo);

                return new PageInfo<>(list);

            }
        };
    }
}
