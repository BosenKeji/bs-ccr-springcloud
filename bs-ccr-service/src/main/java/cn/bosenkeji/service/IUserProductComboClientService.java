package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IProdcutComboClientServiceFallbackFactory;
import cn.bosenkeji.service.fallback.IUserProdcutComboClientServiceFallbackFactory;
import cn.bosenkeji.vo.ProductCombo;
import cn.bosenkeji.vo.UserProductCombo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-18 11:29
 */
@FeignClient(name = "bs-ccr-provider-combo",configuration = FeignClientConfig.class,fallbackFactory = IUserProdcutComboClientServiceFallbackFactory.class)
public interface IUserProductComboClientService {



    @PostMapping("/userproductcombo/")
    boolean add(@RequestBody UserProductCombo userProductCombo);

    @GetMapping("/userproductcombo/listbyusertel/")
    PageInfo<UserProductCombo> listByUserTel(@RequestParam("userTel") String userTel,@RequestParam(value="pageNum",defaultValue = "1") int pageNum,@RequestParam(value="pageSize",defaultValue = "15") int pageSize);




}
