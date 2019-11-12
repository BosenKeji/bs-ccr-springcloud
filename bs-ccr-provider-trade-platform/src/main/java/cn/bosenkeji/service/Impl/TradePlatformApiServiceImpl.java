package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.TradePlatformApiMapper;
import cn.bosenkeji.service.TradePlatformApiService;
import cn.bosenkeji.util.RsaUtils;
import cn.bosenkeji.vo.tradeplatform.TradePlatformApi;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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


    @Override
    public PageInfo listByPage(int pageNum, int pageSize,int userId) {
        PageHelper.startPage(pageNum,pageSize);
        List<TradePlatformApi> tradePlatformApis = this.tradePlatformApiMapper.findAllByUserId(userId);
        return new PageInfo<>(tradePlatformApis);
    }

    @Override
    public TradePlatformApi get(int id) {
        return tradePlatformApiMapper.selectByPrimaryKey(id);
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
                if (keyStr.equals(keyForDB)){
                    return Optional.of(-1);
                }
            }
        }

        return Optional.of(tradePlatformApiMapper.updateByPrimaryKeySelective(tradePlatformApi));
    }

    @Override
    public Optional<Integer> add(TradePlatformApi tradePlatformApi) {
        if (tradePlatformApi.getSecret() == null){
            return Optional.empty();
        }
        List<TradePlatformApi> tradePlatformApis = this.tradePlatformApiMapper.findAllByUserId(tradePlatformApi.getUserId());

        if (!CollectionUtils.isEmpty(tradePlatformApis)){
            for (TradePlatformApi t : tradePlatformApis) {
                String keyStr , keyForDB;
                keyStr = decryptSecretByPrivateKey(tradePlatformApi.getSecret());

                keyForDB = decryptSecretByPrivateKey(t.getSecret());
                System.out.println(keyForDB);
                if (keyStr.equals(keyForDB) || "".equals(keyStr)){
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
}
