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
        return tradePlatformApiMapper.selectByPrimaryKey(id);
    }

    @Override
    public Optional<Integer> update(TradePlatformApi tradePlatformApi) {
        return Optional.ofNullable(tradePlatformApiMapper.updateByPrimaryKeySelective(tradePlatformApi));
    }

    @Override
    public Optional<Integer> add(TradePlatformApi tradePlatformApi) {
        return Optional.ofNullable(tradePlatformApiMapper.insertSelective(tradePlatformApi));
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
    public Optional<Integer> checkExistByKeyAndStatus(int userId,String accessKey, String secretKey,int status) {
        return Optional.ofNullable(tradePlatformApiMapper.checkExistByKeyAndStatus(userId,accessKey, secretKey, status));
    }

    @Override
    public Optional<Integer> checkExistByKey(String accessKey, String secretKey) {
        return Optional.ofNullable(this.tradePlatformApiMapper.checkExistByKey(accessKey, secretKey));
    }

    @Override
    public Optional<Integer> checkExistByUserIdAndNickName(int userId, String nickName) {
        return Optional.ofNullable(tradePlatformApiMapper.checkExistByUserIdAndNickName(userId, nickName));
    }

    @Override
    public List<TradePlatformApi> findAll() {
        return tradePlatformApiMapper.findAll();
    }

    @Override
    public String getPublicKey() {
        String publicKey = null;
        try {
            if (!RsaUtils.checkFile("publicKey.keystore") || !RsaUtils.checkFile("privateKey.keystore")) {
                initKeyPair();
                publicKey = RsaUtils.loadPublicKeyByFile();
            } else {
                publicKey = RsaUtils.loadPublicKeyByFile();
                return publicKey;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return publicKey;
    }

    @Override
    public String getPrivateKey() {
        String privateKey = null;
        try {
            if (!RsaUtils.checkFile("publicKey.keystore") || !RsaUtils.checkFile("privateKey.keystore")) {
                initKeyPair();
                privateKey = RsaUtils.loadPrivateKeyByFile();
            } else {
                privateKey = RsaUtils.loadPrivateKeyByFile();
                return privateKey;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return privateKey;
    }

    private void initKeyPair(){
        //初始化密钥
        //生成密钥对
        Map<String,Object> keyMap = null;
        try {
            keyMap = RsaUtils.initKey();
            //公钥
            String publicKey = Base64.toBase64String(RsaUtils.getPublicKey(keyMap));
            //私钥
            String privateKey = Base64.toBase64String(RsaUtils.getPrivateKey(keyMap));
            // 将密钥对写入到文件
            RsaUtils.loadKeyPairToFile(publicKey,privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
