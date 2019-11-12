package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.CdKeyMapper;
import cn.bosenkeji.service.*;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.utils.ExcelUtil;
import cn.bosenkeji.vo.cdKey.CdKey;
import cn.bosenkeji.vo.cdKey.CdKeyOther;
import cn.bosenkeji.vo.combo.ProductCombo;
import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import cn.bosenkeji.vo.combo.UserProductComboDayByAdmin;
import cn.bosenkeji.vo.product.Product;
import com.aliyun.oss.OSS;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CdKeyServiceImpl implements CdKeyService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CdKeyMapper cdKeyMapper;

    @Autowired
    private IProductComboService iProductComboService;

    @Autowired
    private IProductClientService iProductClientService;

    @Autowired
    private IUserProductComboService iUserProductComboService;

    @Autowired
    private IUserProductComboDayByAdminService iUserProductComboDayByAdminService;


    @Autowired
    private OSS oss;

    private static final String BUCKET_NAME = "bs-follow";
    private static final String CD_KEY_HASH = "cd_key_hash";
    private static final String USED_KEY_HASH = "used_cd_key_hash";
    private static final Integer DEFAULT_STATUS = 1; //未激活
    private static final Integer USED_STATUS = 0; //已激活


    public Result generateCdKeys(Integer num ,Integer productComboId, String prefix, String remark) {
        URL url = null;
        try {
            List<CdKey> cdKeys = generateCdKeys(num, prefix, productComboId, remark);
            cdKeyMapper.insertCdKeyByBatch(cdKeys); //添加到数据库
            //添加到redis  存储为hash
            Map<String, String> cdKeyMap = new HashMap<>();
            List<CdKeyOther> cdKeyOthers = new ArrayList<>();
            //获取产品和套餐名称
            ProductCombo productCombo = iProductComboService.get(productComboId);
            Product product = iProductClientService.getProduct(productCombo.getProductId());

            //时间
            LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.of("+8"));

            cdKeys.forEach((k) -> {
                cdKeyMap.put(k.getKey(), String.valueOf(k.getId()));
                cdKeyOthers.add(new CdKeyOther(k.getId(), k.getKey(), localDateTime.toString(), productComboId, product.getName(), productCombo.getName(), productCombo.getTime(), prefix, remark, 0, ""));
            });

            redisTemplate.opsForHash().putAll(CD_KEY_HASH, cdKeyMap); //cdKeys存入redis

            //生成excel文件
            InputStream inputStream = ExcelUtil.genCdKeyExcel(cdKeyOthers);

            String path = "cdKey/CdKey_" + localDateTime.toLocalDate() + "_" + localDateTime.toLocalTime() + ".xls";

            boolean b = ExcelUtil.uploadExcelToOSS(oss, inputStream, BUCKET_NAME, path);
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (b) {
                Date expiation = new Date(System.currentTimeMillis() + 3600L * 1000 * 24);
                url = oss.generatePresignedUrl(BUCKET_NAME, path, expiation);
            }
        } catch (Exception e) {
            return new Result<>(0,"生成激活码失败");
        }
        return new Result<>(url.getProtocol() + "://" + url.getHost() + url.getPath());
    }


    /**
     * 验证激活码
     * @return cdKey的Id
     */
    private Integer checkCdKey(String key) {
        Integer id = null;
        Boolean isExists = redisTemplate.opsForHash().hasKey(CD_KEY_HASH, key);
        if (isExists) {
            Object o = redisTemplate.opsForHash().get(CD_KEY_HASH, key);
            id = Integer.valueOf(o.toString());
        }
        return id;
    }

    @Override
    public Result activate(Integer userId, String username, String key) {
        Integer cdKeyId = checkCdKey(key);
        if (cdKeyId == null) {
            return new Result<>(0,"激活码不存在，激活失败！");
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            CdKey cdkey = cdKeyMapper.getById(cdKeyId);

            String orderNumber = generateOrderNumber();

            UserProductCombo userProductCombo = new UserProductCombo();
            userProductCombo.setUserId(userId);
            userProductCombo.setOrderNumber(orderNumber);
            userProductCombo.setProductComboId(cdkey.getProductComboId());
            userProductCombo.setRemark(cdkey.getRemark());
            userProductCombo.setStatus(DEFAULT_STATUS);
            userProductCombo.setCreatedAt(timestamp);
            userProductCombo.setUpdatedAt(timestamp);

            int result = iUserProductComboService.add(userProductCombo);
            if (result > 0) {
                clearCdKey(cdKeyId,username,key);
                return new Result<>(1,"激活成功！");
            }
        }
        return new Result<>(0,"激活失败！");
    }

    public Result renew(Integer userId, String username, Integer userProductComboId, String key) {
        Integer cdKeyId = checkCdKey(key);
        if (cdKeyId == null) {
            return new Result<>(0,"激活码不存在，激活码续费失败！");
        } else {
            CdKey cdKey = cdKeyMapper.getById(cdKeyId);
            UserProductCombo userProductCombo = iUserProductComboService.get(userProductComboId);

            if (userProductCombo == null ) {
                return new Result<>(0,"机器人不存在，激活失败！");
            }
            if (userProductCombo.getProductComboId() != cdKey.getProductComboId()) {
                return new Result<>(0,"激活码和产品不匹配，激活失败！");
            }

            ProductCombo productCombo = iProductComboService.get(cdKey.getProductComboId());
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            UserProductComboDay userProductComboDay = new UserProductComboDay();
            userProductComboDay.setUserId(userId);
            userProductComboDay.setUserProductComboId(userProductComboId);
            userProductComboDay.setType(DEFAULT_STATUS);
            userProductComboDay.setNumber(productCombo.getTime());
            userProductComboDay.setStatus(DEFAULT_STATUS);
            userProductComboDay.setCreatedAt(timestamp);
            userProductComboDay.setUpdatedAt(timestamp);

            UserProductComboDayByAdmin userProductComboDayByAdmin = new UserProductComboDayByAdmin();
            userProductComboDayByAdmin.setAdminId(0);
            userProductComboDayByAdmin.setStatus(DEFAULT_STATUS);
            userProductComboDayByAdmin.setCreatedAt(timestamp);
            userProductComboDayByAdmin.setUpdatedAt(timestamp);
            userProductComboDayByAdmin.setOrderNumber(generateOrderNumber());
            userProductComboDayByAdmin.setRemark("cdKey");

            int i = iUserProductComboDayByAdminService.add(userProductComboDay, userProductComboDayByAdmin);

            if ( i>0 ) {
                clearCdKey(cdKeyId,username,key);
                return new Result<>(1,"激活码续费成功！");
            }
            return new Result<>(0,"激活码续费失败！");
        }

    }


    /**
     * 获取激活码分页列表
     * @param pageNum 页码
     * @param pageSize 每页条数
     * @return pageInfo
     */

    @Override
    public PageInfo<CdKeyOther> getCdKeyByPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<CdKey> cdKeys = cdKeyMapper.get();
        List<CdKeyOther> cdKeyOthers = new ArrayList<>();
        List<Integer> productComboIds = cdKeys.stream().map(CdKey::getProductComboId).distinct().collect(Collectors.toList());
        List<ProductCombo> productCombos = iProductComboService.getByIds(productComboIds);
        List<Integer> productIds = productCombos.stream().map(ProductCombo::getProductId).distinct().collect(Collectors.toList());
        Map<Integer, Product> productMap = iProductClientService.listByPrimaryKeys(productIds);
        Collection<Product> products = productMap.values();
        cdKeys.forEach((k) -> {
            Optional<Product> productOptional = Optional.empty();
            Optional<ProductCombo> productComboOptional = productCombos.stream().filter(p -> p.getId() == k.getProductComboId()).findFirst();
            if (productComboOptional.isPresent()) {
                productOptional = products.stream().filter(p -> p.getId() == productComboOptional.get().getProductId()).findFirst();
            }
            CdKeyOther cdKeyOther = new CdKeyOther();
            cdKeyOther.setKey(k.getKey());
            cdKeyOther.setProductComboId(k.getProductComboId());
            cdKeyOther.setId(k.getId());
            cdKeyOther.setCreateAt(k.getCreatedAt().toString());
            cdKeyOther.setProductName(productOptional.get().getName());
            cdKeyOther.setComboName(productComboOptional.get().getName());
            cdKeyOther.setTime(productComboOptional.get().getTime());
            cdKeyOther.setRemark(k.getRemark());
            cdKeyOther.setIsUsed(k.getStatus());
            cdKeyOther.setProfix(k.getKey().split("-")[0]);
            cdKeyOther.setUsername(k.getUsername());
            cdKeyOthers.add(cdKeyOther);
        });
        return new PageInfo<>(cdKeyOthers);

    }

    /**
     * 生成激活码
     * @return 激活码的list
     */
    private List<CdKey> generateCdKeys(Integer num, String prefix, Integer productComboId, String remark) {
        List<CdKey> cdKeys = new ArrayList<>();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        for (int i = 0; i < num; i++) {
            cdKeys.add(new CdKey(generateCdKey(prefix),productComboId,null,remark,DEFAULT_STATUS,timestamp,timestamp));
        }
        if (cdKeys.size() != num ) {
            int temp = num - cdKeys.size();
            for (int i = 0; i < temp; i++) {
                cdKeys.add(new CdKey(generateCdKey(prefix),productComboId,null,remark,DEFAULT_STATUS,timestamp,timestamp));
            }
        }
        return cdKeys;
    }

    private static String generateCdKey(String prefix) {
        return prefix + "-" + RandomStringUtils.randomAlphanumeric(8) + "-" + RandomStringUtils.randomAlphanumeric(8) + "-" + RandomStringUtils.randomAlphanumeric(8) + "-" + RandomStringUtils.randomAlphanumeric(8);
    }


    private static String generateOrderNumber() {
        LocalDate localDate = LocalDate.now();
        return "" + localDate.getYear() + localDate.getMonthValue() + localDate.getDayOfMonth() + RandomStringUtils.random(8,'0','1','2','3','4','5','6','7','8','9','0','a','b','c','d','e','f');
    }

    private void clearCdKey(Integer cdKeyId, String username,String key) {
        cdKeyMapper.updateUsernameAndStatusById(cdKeyId,username,USED_STATUS);
        redisTemplate.opsForHash().put(USED_KEY_HASH,key,cdKeyId.toString());
        redisTemplate.opsForHash().delete(CD_KEY_HASH, key);
    }


}
