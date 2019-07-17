package cn.bosenkeji.service;

import cn.bosenkeji.config.FeignClientConfig;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName IAdminClientService
 * @Description TODO
 * @Author Yu XueWen
 * @Email yuxuewen23@qq.com
 * @Versio V1.0
 **/
@FeignClient(name = "bs-ccr-provider-user-boss",configuration = FeignClientConfig.class)
public interface IAdminClientService {

    @RequestMapping(value="/admin/")
    public PageInfo listByPage();
}
