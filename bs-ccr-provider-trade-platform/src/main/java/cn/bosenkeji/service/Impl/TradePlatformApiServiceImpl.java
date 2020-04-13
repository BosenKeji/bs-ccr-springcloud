package cn.bosenkeji.service.Impl;

import cn.bosenkeji.annotation.cache.BatchCacheRemove;
import cn.bosenkeji.interfaces.CommonResultNumberEnum;
import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.mapper.TradePlatformApiMapper;
import cn.bosenkeji.service.TradePlatformApiService;
import cn.bosenkeji.util.RsaUtils;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author CAJR
 * @create 2019/7/15 14:59
 */

@Service
public class TradePlatformApiServiceImpl implements TradePlatformApiService {

    @Resource
    TradePlatformApiMapper tradePlatformApiMapper;


    /**
     * 与  tradePlatform 关联查询
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    @Cacheable(value = RedisInterface.TRADE_PLATFORM_API_LIST_KEY,key = "#userId+'-'+#pageNum+'-'+#pageSize")
    @Override
    public PageInfo listByPage(int pageNum, int pageSize,int userId) {
        PageHelper.startPage(pageNum,pageSize);
        List<TradePlatformApi> tradePlatformApis = this.tradePlatformApiMapper.findAllByUserId(userId);
        return new PageInfo<>(tradePlatformApis);
    }

    /**
     * 与 tradePlatform 关联查询
     * @param id
     * @return
     */
    @Cacheable(value = RedisInterface.TRADE_PLATFORM_API_ID_KEY,key = "#id",unless = "#result == null")
    @Override
    public TradePlatformApi get(int id) {
        return tradePlatformApiMapper.selectByPrimaryKey(id);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_API_ID_KEY,key = "#tradePlatformApi.id",condition = "#result != null && #result > 0"),
            }
    )
    @BatchCacheRemove(value = "'"+RedisInterface.TRADE_PLATFORM_API_LIST_KEY+"::'+#tradePlatformApi.userId+'-*'",condition = "#result != null && #result.get() > 0")
    @Override
    public Optional<Integer> update(TradePlatformApi tradePlatformApi) {
        if (tradePlatformApi.getSecret() != null){
            List<TradePlatformApi> tradePlatformApis = this.tradePlatformApiMapper.findAllByUserId(tradePlatformApi.getUserId());
            List<TradePlatformApi> tradePlatformApisFilter = tradePlatformApis.stream().filter(t -> t.getId() != tradePlatformApi.getId()).collect(Collectors.toList());
            for (TradePlatformApi t : tradePlatformApisFilter) {
                String keyStr = decryptSecretByPrivateKey(tradePlatformApi.getSecret());

                String keyForDB = decryptSecretByPrivateKey(t.getSecret());
                System.out.println(keyForDB);
                if (keyStr.equals(keyForDB)){
                    return Optional.of(-1);
                }
            }
        }

        return Optional.of(tradePlatformApiMapper.updateByPrimaryKeySelective(tradePlatformApi));
    }

    @BatchCacheRemove(value = "'"+RedisInterface.TRADE_PLATFORM_API_LIST_KEY+"::'+#tradePlatformApi.userId+'-*'",condition = "#result != null")
    @Override
    public Optional<Integer> add(TradePlatformApi tradePlatformApi) {
        if (tradePlatformApi.getSecret() == null){
            return Optional.empty();
        }
        List<TradePlatformApi> tradePlatformApis = this.tradePlatformApiMapper.findAllByUserId(tradePlatformApi.getUserId());

        if (!CollectionUtils.isEmpty(tradePlatformApis)){
            String keyStr = decryptSecretByPrivateKey(tradePlatformApi.getSecret());
            System.out.println(keyStr);
            for (TradePlatformApi t : tradePlatformApis) {
                if ("".equals(keyStr)){
                 return Optional.of(-2);
                }
                String keyForDB = decryptSecretByPrivateKey(t.getSecret());
                System.out.println(keyForDB);
                if (keyStr.equals(keyForDB)){
                    return Optional.of(-1);
                }
            }
        }
        return Optional.of(tradePlatformApiMapper.insertSelective(tradePlatformApi));
    }

    /**
     * 解密
     * @param secret 密文
     * @return
     */
    private  String decryptSecretByPrivateKey(String secret){
        String apiKey = "";

        //本地私钥文件是否为空
        if (RsaUtils.checkFileIsNull(RsaUtils.PRIVATE_KEY_FILE_NAME) && RsaUtils.checkKeyPairOnOSS()){
            RsaUtils.downloadPrivateKeyByOSS();
        }

        try {
            String privateKeyStr = RsaUtils.loadPrivateKeyByFile();
            String priKeyFormat = RsaUtils.formatPrivateKey(privateKeyStr);
            byte[] code = Base64.decode(secret);
            byte[] decode = RsaUtils.decryptByPrivateKey(code,Base64.decode(priKeyFormat),0);
            apiKey = new String(decode);
        } catch (Exception e) {
            return apiKey;
        }
        return apiKey;
    }

    //清空缓存在controller
    @Override
    public Optional<Integer> delete(int id) {
        return Optional.of(tradePlatformApiMapper.deleteByPrimaryKey(id)) ;
    }

    @Override
    public TradePlatformApi getByUserId(int userId) {
        return tradePlatformApiMapper.selectByUserId(userId);
    }

    @Override
    public Optional<Integer> checkExistBySignAndStatus(int tradePlatformId,int userId,String sign,int status) {
        return Optional.of(tradePlatformApiMapper.checkExistByKeyAndStatus(userId,sign, status));
    }

    @Override
    public Optional<Integer> checkExistByUserIdAndNickName(int userId, String nickName) {
        return Optional.of(tradePlatformApiMapper.checkExistByUserIdAndNickName(userId, nickName));
    }

    @Override
    public List<TradePlatformApi> findAll() {
        return tradePlatformApiMapper.findAll();
    }

    @Override
    public List<TradePlatformApi> findAllBySign(String sign) {
        return this.tradePlatformApiMapper.findAllBySign(sign);
    }

    /**
     * add by xivin
     * 更新 API isBound 是否被绑定状态
     * @param id
     * @param isBound
     * @return
     */
    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_API_ID_KEY,key = "#id",condition = "#result > 0")
            }
    )
    @BatchCacheRemove(value = "'"+RedisInterface.TRADE_PLATFORM_API_LIST_KEY+"::'+#userId+'-*'",condition = "#result > 0")
    @Override
    public int updateIsBound(int id, int isBound, int userId) {
        System.out.println("userId = " + userId);
        int isExist = tradePlatformApiMapper.checkExistByIdAndUserId(id, userId);
        if(id >0 && isExist > 0){
            return this.tradePlatformApiMapper.updateIsBound(id,isBound, Timestamp.valueOf(LocalDateTime.now()));
        }
        return CommonResultNumberEnum.FAIL;
    }
}
