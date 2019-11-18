package cn.bosenkeji.service.fallback;

import cn.bosenkeji.service.IUserClientService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.User;
import com.github.pagehelper.PageInfo;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            public Result checkExistById(int id) {
                return new Result<>(0,"hystrix");
            }

            @Override
            public PageInfo listByPage(Integer pageNum, Integer pageSize) {
                User user=new User();
                user.setUsername("hystrix");
                List<User> list=new ArrayList<>();
                list.add(user);
                return new PageInfo<>(list);
            }

            @Override
            public PageInfo listBySearch(Integer status, String tel, int pageNum, int pageSize) {
                User user=new User();
                user.setUsername("hystrix");
                List<User> list=new ArrayList<>();
                list.add(user);
                return new PageInfo<>(list);
            }

            @Override
            public Result updateStatusById(Integer id, Integer status) {
                return new Result<>(0,"hystrix");
            }

            @Override
            public Map<Integer, User> listByIds(List<Integer> ids) {
                Map<Integer,User> map=new HashMap<>();
                User user=new User();
                user.setUsername("hystrix");
                user.setTel("hystrix");
                (map).put(-1,user);
                return map;
            }

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
                return new Result<>(0,"fail hystrix");
            }

            @Override
            public Result updateUser(User user) {
                return new Result<>(0,"fail hystrix");
            }

            @Override
            public Result deleteOneUser(int id) {
                return new Result<>(0,"fail hystrix");
            }

            @Override
            public User getOneUserByTel(String tel) {

                User user=new User();
                user.setUsername("hystrix");
                return user;
            }

            @Override
            public Result updateUserPassword(int id, String password) {
                return new Result<>(0,"hystrix");
            }

            @Override
            public Result updateUserTel(int id, String tel) {
                return new Result<>(0,"hystrix");
            }

            @Override
            public Result updateUserUsername(int id, String username) {
                return new Result<>(0,"hystrix");
            }

            @Override
            public Result updateBinding(int id, int isBinding) {
                return new Result<>(-1,"hystrix");
            }

            @Override
            public Result updatePasswordByTel(String tel, String password) {
                return new Result<>(-1,"hystrix");
            }
        };
    }
}
