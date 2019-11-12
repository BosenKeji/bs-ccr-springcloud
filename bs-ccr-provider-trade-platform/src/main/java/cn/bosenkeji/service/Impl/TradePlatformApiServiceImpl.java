package cn.bosenkeji.service.Impl;

import cn.bosenkeji.interfaces.CommonResultNumberEnum;
import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.mapper.TradePlatformApiMapper;
import cn.bosenkeji.service.TradePlatformApiService;
import cn.bosenkeji.service.TradePlatformService;
import cn.bosenkeji.util.RsaUtils;
import cn.bosenkeji.vo.tradeplatform.TradePlatform;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApiListResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author CAJR
 * @create 2019/7/15 14:59
 */

@Service
public class TradePlatformApiServiceImpl implements TradePlatformApiService {

    @Resource
    TradePlatformApiMapper tradePlatformApiMapper;


    @Override
    public PageInfo listByPage(int pageNum, int pageSize,int userId) {
        PageHelper.startPage(pageNum,pageSize);
        List<TradePlatformApiListResult> tradePlatformApis = this.tradePlatformApiMapper.findAllByUser(userId);
        if (!CollectionUtils.isEmpty(tradePlatformApis)){
            tradePlatformApis.forEach(t->{
                if (t.getSecret() != null){
                    String keyPair = decryptSecretByPrivateKey(t.getSecret());
                    int index = keyPair.indexOf("_");
                    String aKey = keyPair.substring(0,index);
                    t.setAccessKey(aKey);
                }
            });
        }
        return new PageInfo<>(tradePlatformApis);
    }

    @Override
    public TradePlatformApi get(int id) {
        TradePlatformApi tradePlatformApi = tradePlatformApiMapper.selectByPrimaryKey(id);
        if (tradePlatformApi.getSecret() != null){
            String secret = tradePlatformApi.getSecret();
            tradePlatformApi.setSecret(decryptSecretByPrivateKey(secret));
        }
        return tradePlatformApi;
    }

    @Override
    public Optional<Integer> update(TradePlatformApi tradePlatformApi) {
        if (tradePlatformApi.getSecret() != null){
            List<TradePlatformApi> tradePlatformApis = this.tradePlatformApiMapper.findAllByUserId(tradePlatformApi.getUserId());
            List<TradePlatformApi> tradePlatformApisFilter = tradePlatformApis.stream().filter(t -> t.getId() != tradePlatformApi.getId()).collect(Collectors.toList());
            for (TradePlatformApi t : tradePlatformApisFilter) {
                String keyStr = decryptSecretByPrivateKey(tradePlatformApi.getSecret());

                String keyForDB = decryptSecretByPrivateKey(t.getSecret());
                System.out.println(keyForDB);
                assert keyStr != null;
                if (keyStr.equals(keyForDB)){
                    return Optional.of(-1);
                }
            }
        }

        return Optional.of(tradePlatformApiMapper.updateByPrimaryKeySelective(tradePlatformApi));
    }

    @Override
    public Optional<Integer> add(TradePlatformApi tradePlatformApi) {
        List<TradePlatformApi> tradePlatformApis = this.tradePlatformApiMapper.findAllByUserId(tradePlatformApi.getUserId());

        if (!CollectionUtils.isEmpty(tradePlatformApis)){
            for (TradePlatformApi t : tradePlatformApis) {
                String keyStr , keyForDB;
                keyStr = decryptSecretByPrivateKey(tradePlatformApi.getSecret());

                keyForDB = decryptSecretByPrivateKey(t.getSecret());
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
            return null;
        }
        return apiKey;
    }


    @Override
    public Optional<Integer> delete(int id) {
        return Optional.of(tradePlatformApiMapper.deleteByPrimaryKey(id)) ;
    }

    @Override
    public TradePlatformApi getByUserId(int userId) {
        return tradePlatformApiMapper.selectByUserId(userId);
    }

    @Override
    public Optional<Integer> checkExistByKeyAndStatus(int userId,String sign,int status) {
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

    /**
     * add by xivin
     * 更新 API isBound 是否被绑定状态
     * @param id
     * @param isBound
     * @return
     */
    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_API_ID_KEY,key = "#id"),
                    @CacheEvict(value = RedisInterface.TRADE_PLATFORM_API_LIST_KEY,allEntries = true)
            }
    )
    @Override
    public int updateIsBound(int id, int isBound) {
        if(id >0)
            return this.tradePlatformApiMapper.updateIsBound(id,isBound, Timestamp.valueOf(LocalDateTime.now()));
        return CommonResultNumberEnum.FAIL;
    }
}
