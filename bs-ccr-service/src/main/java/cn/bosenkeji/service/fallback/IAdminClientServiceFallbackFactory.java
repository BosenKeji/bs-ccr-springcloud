package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IAdminClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.Admin;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @ClassName IAdminClientServiceFallbackFactory
 * @Description TODO
 * @Author hjh
 * @Date 2019-07-31 17:18
 * @Version 1.0
 */
@Component
public class IAdminClientServiceFallbackFactory implements FallbackFactory<IAdminClientService> {

    @Override
    public IAdminClientService create(Throwable throwable) {
        return new IAdminClientService() {
            @Override
            public PageInfo listByPage(int pageNum, int pageSize) {
                PageInfo pageInfo = new PageInfo();
                List<Admin> list = new ArrayList<>();
                Admin admin = new Admin();
                list.add(admin);
                pageInfo.setList(list);
                return pageInfo;
            }

            @Override
            public Optional<Admin> get(int id) {
                Admin admin = new Admin();
                admin.setAccount("failed");
                return Optional.of(admin);
            }

            @Override
            public Result add(Admin admin) {
                return new Result(1,"failed");
            }

            @Override
            public Result update(Admin admin) {
                return new Result(1,"failed");
            }

            @Override
            public Result delete(int id) {
                return new Result(1,"failed");
            }

            @Override
            public Optional<Admin> selectByAccount(String account) {
                Admin admin = new Admin();
                admin.setAccount("failed");
                return Optional.of(admin);
            }
        };
    }

}
