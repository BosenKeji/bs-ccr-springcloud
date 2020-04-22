package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.CdKeyMapper;
import cn.bosenkeji.service.*;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.utils.ExcelUtil;
import cn.bosenkeji.vo.User;
import cn.bosenkeji.vo.cdKey.*;
import cn.bosenkeji.vo.combo.ProductCombo;
import cn.bosenkeji.vo.combo.UserProductCombo;
import cn.bosenkeji.vo.combo.UserProductComboDay;
import cn.bosenkeji.vo.combo.UserProductComboDayByAdmin;
import cn.bosenkeji.vo.product.Product;
import com.aliyun.oss.OSS;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CdKeyServiceImpl implements CdKeyService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Resource
    private CdKeyMapper cdKeyMapper;

    @Autowired
    private IProductComboService iProductComboService;

    @Autowired
    private IProductClientService iProductClientService;

    @Autowired
    private IUserProductComboService iUserProductComboService;

    @Autowired
    private IUserProductComboDayByAdminService iUserProductComboDayByAdminService;

    @Resource
    private IUserClientService iUserClientService;


    @Autowired
    private OSS oss;

    private static final String BUCKET_NAME = "bs-follow";
    private static final String CD_KEY_HASH = "cd_key_hash";
    private static final Integer DEFAULT_STATUS = 1; //未激活
    private static final Integer USED_STATUS = 0; //已激活


    /**
     * 生成激活码 返回给前端
     * @param param 生成激活码的参数
     * @return result
     */
    public Result generateCdKeys(GenerateCdKeyParam param) {
        URL url = null;
        try {
            //套餐处理
            ProductCombo productCombo = productComboHandle(param);
            if (productCombo == null) { //自定义套餐失败
                return new Result<>(0,"自定义套餐失败！");
            }

            List<CdKey> cdKeys = generateCdKeys(param.getNumber(), param.getPrefix(), productCombo.getId(), param.getRemark());
            cdKeyMapper.insertCdKeyByBatch(cdKeys); //添加到数据库
            //添加到redis  存储为hash
            Map<String, String> cdKeyMap = new HashMap<>();
            List<CdKeyOther> cdKeyOthers = new ArrayList<>();

            Product product = iProductClientService.getProduct(productCombo.getProductId());

            //时间
            LocalDateTime localDateTime = LocalDateTime.now(ZoneOffset.of("+8"));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatLocalDateTime = dtf.format(localDateTime);

            cdKeys.forEach((k) -> {
                cdKeyMap.put(k.getKey(), String.valueOf(k.getId()));
                cdKeyOthers.add(new CdKeyOther(k.getId(), k.getKey(), formatLocalDateTime, param.getProductComboId(), product.getName(), productCombo.getName(), productCombo.getTime(), param.getPrefix(), param.getRemark(), 0, ""));
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
     * 验证激活码 redis
     * @return cdKey的Id
     */
    private CdKey checkCdKey(String key) {
        CdKey cdKey;
        //redis验证
        Boolean isExists = redisTemplate.opsForHash().hasKey(CD_KEY_HASH, key);
        if (isExists) {
            Object o = redisTemplate.opsForHash().get(CD_KEY_HASH, key);
            Integer id = Integer.valueOf(o.toString());
            cdKey = cdKeyMapper.getById(id);
        } else { //数据库验证
            cdKey = cdKeyMapper.getByKeyAndStatus(key, DEFAULT_STATUS);
        }
        return cdKey;
    }

    /**
     * 激活码激活
     * @param param 激活需要的参数
     * @return result
     */
    @Override
    public Result activate(ActivateCdKeyUserParam param) {
        CdKey cdKey = checkCdKey(param.getCdKey());
        if (cdKey == null) {
            return new Result<>(0,"激活码不存在，激活失败！");
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            String orderNumber = generateOrderNumber();
            UserProductCombo userProductCombo = new UserProductCombo();
            userProductCombo.setUserId(param.getUserId());
            userProductCombo.setOrderNumber(orderNumber);
            userProductCombo.setProductComboId(cdKey.getProductComboId());
            userProductCombo.setRemark(cdKey.getRemark());
            userProductCombo.setStatus(DEFAULT_STATUS);
            userProductCombo.setCreatedAt(timestamp);
            userProductCombo.setUpdatedAt(timestamp);

            int result = iUserProductComboService.add(userProductCombo);
            if (result > 0) {
                clearCdKey(cdKey.getId(),param.getUserId(),param.getCdKey(),result);
                return new Result<>(1,"激活成功！");
            }
        }
        return new Result<>(0,"激活失败！");
    }

    /**
     * 激活码续费
     * @param param 续费需要的参数
     * @return result
     */

    @Override
    public Result renew(RenewCdKeyUserParam param) {
        CdKey cdKey = checkCdKey(param.getCdKey());
        if (cdKey == null) {
            return new Result<>(0,"激活码不存在，激活码续费失败！");
        } else {
            UserProductCombo userProductCombo = iUserProductComboService.get(param.getUserProductComboId());

            if (userProductCombo == null ) {
                return new Result<>(0,"机器人不存在，激活失败！");
            }

            if (userProductCombo.getUserId() != param.getUserId()) {
                return new Result(0,"系统检测到该机器人不属于您，请再次检查机器人id是否正确");
            }

            ProductCombo productCombo = iProductComboService.get(cdKey.getProductComboId());
            if (userProductCombo.getProductCombo().getProductId() != productCombo.getProductId()) {
                return new Result<>(0,"激活码和产品不匹配，激活失败！");
            }

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            UserProductComboDay userProductComboDay = new UserProductComboDay();
            userProductComboDay.setUserId(param.getUserId());
            userProductComboDay.setUserProductComboId(param.getUserProductComboId());
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
                clearCdKey(cdKey.getId(),param.getUserId(),param.getCdKey(),param.getUserProductComboId());
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
        PageInfo<CdKey> cdKeyPageInfo = new PageInfo<>(cdKeys);

        List<CdKeyOther> cdKeyOthers = convertToCdKeyOther(cdKeyPageInfo.getList());
        PageInfo<CdKeyOther> cdKeyOtherPageInfo = new PageInfo<>();
        cdKeyOtherPageInfo.setTotal(cdKeyPageInfo.getTotal());
        cdKeyOtherPageInfo.setList(cdKeyOthers);
        cdKeyOtherPageInfo.setPageNum(cdKeyPageInfo.getPageNum());
        cdKeyOtherPageInfo.setPageSize(cdKeyPageInfo.getPageSize());
        return cdKeyOtherPageInfo;

    }

    /**
     * 生成激活码
     * @return 激活码的list
     */
    private List<CdKey> generateCdKeys(Integer num, String prefix, Integer productComboId, String remark) {
        List<CdKey> cdKeys = new ArrayList<>();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        for (int i = 0; i < num; i++) {
            cdKeys.add(new CdKey(generateCdKey(prefix),productComboId,remark,DEFAULT_STATUS,timestamp,timestamp));
        }
        if (cdKeys.size() != num ) {
            int temp = num - cdKeys.size();
            for (int i = 0; i < temp; i++) {
                cdKeys.add(new CdKey(generateCdKey(prefix),productComboId,remark,DEFAULT_STATUS,timestamp,timestamp));
            }
        }
        return cdKeys;
    }


    /**
     * 按条件获取cdKey
     * @param cdKey cdKey对象
     * @return cdKey分页
     */

    @Override
    public PageInfo<CdKeyOther> getCdKeyBySearch(String cdKey,String username, Integer isUsed, Integer userProductComboId, Integer sort, Integer pageNum, Integer pageSize) {

        String orderBy = "created_at ";
        if (sort == 1) {
            orderBy = orderBy + "asc";
        } else {
            orderBy = orderBy + "desc";
        }

        PageHelper.startPage(pageNum,pageSize,orderBy);
        CdKey c = new CdKey();
        c.setKey(cdKey);

        if (StringUtils.isNotBlank(username)) {
            User oneUserByTel = iUserClientService.getOneUserByTel(username);
            if (oneUserByTel == null) {
                return new PageInfo<>();
            }
            c.setUserId(oneUserByTel.getId());
        }
        c.setStatus(isUsed);
        c.setUserProductComboId(userProductComboId);

        List<CdKey> cdKeys = cdKeyMapper.getBySearch(c);
        PageInfo<CdKey> cdKeyPageInfo = new PageInfo<>(cdKeys);

        List<CdKeyOther> cdKeyOthers = convertToCdKeyOther(cdKeyPageInfo.getList());

        return convertPageInfo(cdKeyPageInfo, cdKeyOthers);
    }



    private ProductCombo productComboHandle(GenerateCdKeyParam param) {
        ProductCombo productCombo;

        if (param.getProductComboId() == null || param.getProductComboId() == 0 ) {  // 自定义套餐
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now(ZoneOffset.of("+8")));
            productCombo = new ProductCombo();
            productCombo.setName("自定义" + param.getTime());
            productCombo.setPrice(0);
            productCombo.setTime(param.getTime());
            productCombo.setRemark(param.getRemark());
            productCombo.setIsCustomized(1);
            productCombo.setStatus(DEFAULT_STATUS);
            productCombo.setProductId(param.getProductId());
            int result = iProductComboService.addBySelective(productCombo);
            if (result == 0) {
                return null;
            }
        } else { //非自定义套餐
            productCombo = iProductComboService.get(param.getProductComboId());
        }
        return productCombo;
    }

    private List<CdKeyOther> convertToCdKeyOther(List<CdKey> cdKeys) {
        try {


            List<CdKeyOther> cdKeyOthers = new ArrayList<>();
            if (CollectionUtils.isEmpty(cdKeys)) {
                return cdKeyOthers;
            } else {
                List<Integer> productComboIds = cdKeys.stream().map(CdKey::getProductComboId).distinct().collect(Collectors.toList());
                List<ProductCombo> productCombos = iProductComboService.getByIds(productComboIds);
                List<Integer> allUserIds = new ArrayList<>();
                cdKeys.forEach(cdKey -> {
                    allUserIds.add(cdKey.getUserId());
                });
                List<Integer> userIds = allUserIds.stream().distinct().collect(Collectors.toList());
                Map<Integer, User> userMap = iUserClientService.listByIds(userIds);
                Collection<User> users = userMap.values();
                List<Integer> productIds = productCombos.stream().map(ProductCombo::getProductId).distinct().collect(Collectors.toList());
                Map<Integer, Product> productMap = iProductClientService.listByPrimaryKeys(productIds);
                Collection<Product> products = productMap.values();
                cdKeys.forEach((k) -> {
                    Optional<Product> productOptional = Optional.empty();
                    Optional<User> userOptional = Optional.empty();
                    userOptional = users.stream().filter(user -> user.getId() == k.getUserId()).findFirst();
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
                    if (userOptional.isPresent()) {
                        cdKeyOther.setUsername(userOptional.get().getTel());
                    }
                    cdKeyOther.setUserProductComboId(k.getUserProductComboId());
                    if (null != k.getUserProductCombo()) {
                        cdKeyOther.setRobotManageStatus(k.getUserProductCombo().getRunStatus());
                    }

                    cdKeyOthers.add(cdKeyOther);
                });
            }
            return cdKeyOthers;
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private static String generateCdKey(String prefix) {
        String s = RandomStringUtils.randomAlphanumeric(8) + "-" + RandomStringUtils.randomAlphanumeric(8) + "-" + RandomStringUtils.randomAlphanumeric(8) + "-" + RandomStringUtils.randomAlphanumeric(8);
        if (StringUtils.isNotBlank(prefix)) {
            s = prefix + "-" + s;
        }
        return s;
    }


    private static String generateOrderNumber() {
        LocalDate localDate = LocalDate.now();
        return "" + localDate.getYear() + localDate.getMonthValue() + localDate.getDayOfMonth() + RandomStringUtils.random(8,'0','1','2','3','4','5','6','7','8','9','0','a','b','c','d','e','f');
    }

    private void clearCdKey(Integer cdKeyId, int userId,String key, int userProductComboId) {
        cdKeyMapper.updateUserIdAndStatusById(cdKeyId,userId,USED_STATUS,userProductComboId);
        redisTemplate.opsForHash().delete(CD_KEY_HASH, key);
    }

    private PageInfo<CdKeyOther> convertPageInfo(PageInfo<CdKey> cdKeyPageInfo, List<CdKeyOther> cdKeyOthers) {

        PageInfo<CdKeyOther> cdKeyOtherPageInfo = new PageInfo<>();
        cdKeyOtherPageInfo.setTotal(cdKeyPageInfo.getTotal());
        cdKeyOtherPageInfo.setList(cdKeyOthers);
        cdKeyOtherPageInfo.setPageNum(cdKeyPageInfo.getPageNum());
        cdKeyOtherPageInfo.setPageSize(cdKeyPageInfo.getPageSize());
        cdKeyOtherPageInfo.setIsFirstPage(cdKeyPageInfo.isIsFirstPage());
        cdKeyOtherPageInfo.setIsLastPage(cdKeyPageInfo.isIsLastPage());
        cdKeyOtherPageInfo.setHasNextPage(cdKeyPageInfo.isHasNextPage());
        cdKeyOtherPageInfo.setHasPreviousPage(cdKeyPageInfo.isHasPreviousPage());
        cdKeyOtherPageInfo.setEndRow(cdKeyPageInfo.getEndRow());
        cdKeyOtherPageInfo.setPages(cdKeyPageInfo.getPages());
        cdKeyOtherPageInfo.setPrePage(cdKeyPageInfo.getPrePage());
        cdKeyOtherPageInfo.setNextPage(cdKeyPageInfo.getNextPage());
        cdKeyOtherPageInfo.setSize(cdKeyPageInfo.getSize());
        cdKeyOtherPageInfo.setStartRow(cdKeyPageInfo.getStartRow());
        return cdKeyOtherPageInfo;
    }


}
