package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.TradePlatformApiMapper;
import cn.bosenkeji.service.TradePlatformApiService;
import cn.bosenkeji.service.TradePlatformService;
import cn.bosenkeji.util.RsaUtils;
import cn.bosenkeji.vo.tradeplatform.TradePlatform;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

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
        List<TradePlatformApi> tradePlatformApis = this.tradePlatformApiMapper.findAllByUserId(userId);
        return new PageInfo<>(tradePlatformApis);
    }

    @Override
    public TradePlatformApi get(int id) {
        TradePlatformApi tradePlatformApi = tradePlatformApiMapper.selectByPrimaryKey(id);
        if (tradePlatformApi != null){
            String secret = tradePlatformApi.getSecret();
            tradePlatformApi.setSecret(decryptSecretByPrivateKey(secret));
        }

        return tradePlatformApi;
    }

    @Override
    public Optional<Integer> update(TradePlatformApi tradePlatformApi) {
        return Optional.ofNullable(tradePlatformApiMapper.updateByPrimaryKeySelective(tradePlatformApi));
    }

    @Override
    public Optional<Integer> add(TradePlatformApi tradePlatformApi) {
        List<TradePlatformApi> tradePlatformApis = this.tradePlatformApiMapper.findAllByUserId(tradePlatformApi.getUserId());

        if (!CollectionUtils.isEmpty(tradePlatformApis)){
            for (TradePlatformApi t : tradePlatformApis) {
                String keyStr , keyForDB;
                keyStr = decryptSecretByPrivateKey(tradePlatformApi.getSecret());
                int indexKeyStr =keyStr.indexOf("_",2);
                keyStr = keyStr.substring(0,indexKeyStr);

                keyForDB = decryptSecretByPrivateKey(t.getSecret());
                int indexKeyForDB = keyForDB.indexOf("_",2);
                keyForDB = keyForDB.substring(0,indexKeyForDB);
                System.out.println(indexKeyForDB);
                if (keyStr.equals(keyForDB)){
                    return Optional.of(-1);
                }
            }
        }
        return Optional.ofNullable(tradePlatformApiMapper.insertSelective(tradePlatformApi));
    }

    /**
     * 解密
     * @param secret 密文
     * @return
     */
    private static String decryptSecretByPrivateKey(String secret){
        String apiKey = "";
        if (RsaUtils.checkKeyPairOnOSS()){
            RsaUtils.downloadPrivateKeyByOSS();
            try {
                String privateKeyStr = RsaUtils.loadPrivateKeyByFile();
                String priKeyFormat = RsaUtils.formatPrivateKey(privateKeyStr);
                byte[] code = Base64Utils.decodeFromString(secret);
                byte[] decode = RsaUtils.decryptByPrivateKey(code,Base64.decode(priKeyFormat),0);
                apiKey = Base64.toBase64String(decode);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return apiKey;
    }

    public static void main(String[] args) {
        String str = "tWHwUOeFgm8jz5XdDguZKLIk1V8EmH2npXnyw/IBGe4kozYtoxpS3EXOE9Jm5DoxzbejNbHz+DevtraB9J6UZN73NX6RSAtsuIiWaQDHIpSe6Yn2z/75xGD5+c2NDb5S+JDAuV0UW5jYPMD4C8OQoKZBlDv8CJp28dRQY1pmGAQ=";
        System.out.println(decryptSecretByPrivateKey(str));
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.ofNullable(tradePlatformApiMapper.deleteByPrimaryKey(id)) ;
    }

    @Override
    public TradePlatformApi getByUserId(int userId) {
        return tradePlatformApiMapper.selectByUserId(userId);
    }

    @Override
    public Optional<Integer> checkExistByKeyAndStatus(int userId,String sign,int status) {
        return Optional.ofNullable(tradePlatformApiMapper.checkExistByKeyAndStatus(userId,sign, status));
    }


    @Override
    public Optional<Integer> checkExistByUserIdAndNickName(int userId, String nickName) {
        return Optional.ofNullable(tradePlatformApiMapper.checkExistByUserIdAndNickName(userId, nickName));
    }

    @Override
    public List<TradePlatformApi> findAll() {
        return tradePlatformApiMapper.findAll();
    }
}
