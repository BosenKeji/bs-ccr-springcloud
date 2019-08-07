package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IUserClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.User;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author xivin
 * @ClassName cn.bosenkeji.service.fallback
 * @Version V1.0
 * @create 2019-07-31 19:17
 */
@Component
public class IUserClientServiceFallbackFactory implements FallbackFactory<IUserClientService> {
    @Override
    public IUserClientService create(Throwable throwable) {
        return new IUserClientService() {

            @Override
            public User getOneUser(int id) {
                User user=new User();
                user.setUsername("hystrix");
                return user;
            }

            @Override
            public User getOneUserByUsername(String username) {
                User user=new User();
                user.setUsername("hystrix");
                return user;
            }

            @Override
            public Result addOneUser(User user) {
                return new Result("0","fail hystrix");
            }

            @Override
            public Result updateUser(User user) {
                return new Result("0","fail hystrix");
            }

            @Override
            public Result deleteOneUser(int id) {
                return new Result("0","fail hystrix");
            }

            @Override
            public User getOneUserByTel(String tel) {

                User user=new User();
                user.setUsername("hystrix");
                return user;
            }
        };
    }
}
