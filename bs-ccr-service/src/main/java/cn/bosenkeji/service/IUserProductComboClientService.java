package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import cn.bosenkeji.service.fallback.IUserProductComboClientServiceFallbackFactory;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.combo.UserProductCombo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service
 * @Version V1.0
 * @create 2019-07-18 11:29
 */
@FeignClient(name = "bs-ccr-provider-product-combo",configuration = FeignClientConfig.class,fallbackFactory = IUserProductComboClientServiceFallbackFactory.class)
public interface IUserProductComboClientService {



    @PostMapping("/user_product_combo/")
    Result add(@RequestBody UserProductCombo userProductCombo);

    @GetMapping("/user_product_combo/list_by_user_tel/")
    PageInfo listByUserTel(@RequestParam("userTel") String userTel,@RequestParam(value="pageNum") int pageNum,@RequestParam(value="pageSize") int pageSize);

    @GetMapping("/user_product_combo/list_by_user_id/")
    PageInfo listByUserId(@RequestParam("userId") int userId,@RequestParam(value="pageNum") int pageNum,@RequestParam(value="pageSize") int pageSize);

    @GetMapping("/user_product_combo/{id}")
    UserProductCombo getUserProductCombo(@PathVariable("id") int id);

    @GetMapping("/user_product_combo/list_by_ids")
    Map<Integer,UserProductCombo> getByPrimaryKeys(@RequestParam("ids") List<Integer> ids);

    @DeleteMapping("/user_product_combo/{id}")
    Result delete(@PathVariable("id") int id);


}
