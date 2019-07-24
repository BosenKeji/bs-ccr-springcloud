package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IUserProductComboClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.combo.UserProductCombo;
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
@FeignClient(name = "bs-ccr-provider-combo",configuration = FeignClientConfig.class,fallbackFactory = IUserProductComboClientServiceFallbackFactory.class)
public interface IUserProductComboClientService {



    @PostMapping("/user_product_combo/")
    Result add(@RequestBody UserProductCombo userProductCombo);

    @GetMapping("/user_product_combo/list_by_user_tel/")
    PageInfo listByUserTel(@RequestParam("userTel") String userTel,@RequestParam(value="pageNum") int pageNum,@RequestParam(value="pageSize") int pageSize);




}
