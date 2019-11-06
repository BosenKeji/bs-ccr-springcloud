package cn.bosenkeji.controller;

import cn.bosenkeji.annotation.cache.ZSetCacheEvict;
import cn.bosenkeji.interfaces.RedisInterface;
import cn.bosenkeji.service.UserService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.User;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "User 用户相关接口",value = "提供用户相关的 Rest API接口")
@Validated
@CacheConfig(cacheNames = "ccr:user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation(value = "获取用户列表接口 ",httpMethod = "GET",nickname = "getUserListWithPage")
    @GetMapping("/")
    public PageInfo listByPage(@RequestParam(value = "pageNum",required = false,defaultValue = "1") @Min(1) Integer pageNum,
                               @RequestParam(value = "pageSize",required = false,defaultValue = "10") @Min(1)  Integer pageSize)
    {
        return this.userService.listByPage(pageNum,pageSize);
    }

    //@Cacheable(value = RedisInterface.USER_REDIS_ID_KEY,key = "#id",unless = "#result==null")
    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个用户接口", httpMethod = "GET", nickname = "getOneUser")
    public User get(@PathVariable("id") @Min(1) @ApiParam(value = "用户IID", required = true, type = "integer", example = "1") int id) {
        return userService.get(id);
    }



    @GetMapping("/get_by_username")
    @ApiOperation(value = "通过用户名获取单个用户接口", httpMethod = "GET", nickname = "getOneUserByUsername")
    public User getByUsername(@RequestParam("username") @NotNull @ApiParam(value = "用户名", required = true, type = "string", example = "zhangsan") String username) {
        Integer id=userService.getIdByUsername(username);
        if(id==null||id<1)
            return null;
        return this.get(userService.getIdByUsername(username));
        //return userService.getByUsername(username);
    }



    @GetMapping("/get_by_tel")
    @ApiOperation(value = "通过用户电话获取单个用户接口", httpMethod = "GET", nickname = "getOneUserByTel")
    public User getByTel(@RequestParam("tel") @NotNull @ApiParam(value = "用户电话", required = true, type = "string", example = "13556559840") String tel) {
        return userService.getByTel(tel);
    }

    @PostMapping("/")
    @ApiOperation(value = "添加单个用户接口", httpMethod = "POST", nickname = "addOneUser")
    public Result add(@RequestBody @NotNull @ApiParam(value = "用户实体", required = true, type = "string") User user) {

        if(userService.checkExistByUsrename(user.getUsername())!=0) {
            return new Result<>("0","此用户名已存在！");
        }
        if(userService.checkExistByTel(user.getTel())!=0) {
            return new Result<>("0","此电话号码已注册");
        }

        user.setPassword((new BCryptPasswordEncoder()).encode(user.getPassword()));
        user.setIsBinding(0);
        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        user.setStatus(1);

        return new Result<>(userService.add(user));
    }

    @ZSetCacheEvict(key = RedisInterface.USER_REDIS_USERNAME_KEY,score = "#user.id")
    //@CacheWithHash(name = RedisInterface.USER_REDIS_USERNAME_KEY,key = "#user.username",value = "#user.id",unless = "#result == 0",operation = HashOperation.SET)
    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.USER_REDIS_ID_KEY,key = "#user.id"),
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_UPC_ID_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_TEL_KEY,allEntries = true)
            }
    )
    @PutMapping("/")
    @ApiOperation(value = "更新用户接口",httpMethod = "PUT",nickname = "updateUser")
    public Result update(@RequestBody @NotNull @ApiParam(value = "用户实体", required = true, type = "string") User user) {


        user.setPassword((new BCryptPasswordEncoder()).encode(user.getPassword()));

        user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        return new Result<>(userService.updateByPrimaryKeySelective(user));

    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.USER_REDIS_ID_KEY,key = "#id"),
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_UPC_ID_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_TEL_KEY,allEntries = true)
            }
    )
    @PutMapping("/update_password/{id}")
    @ApiOperation(value = "修改用户密码",httpMethod = "PUT",nickname = "updatePassword")
    public Result updatePassword(@PathVariable("id") @Min(1)
                                     @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int id,
                                 @RequestParam("password") @NotNull @ApiParam(value = "用户密码",required = true,type = "string",example = "123456") String password) {
        User user=new User();
        user.setId(id);
        user.setPassword((new BCryptPasswordEncoder()).encode(password));
        user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(userService.updateByPrimaryKeySelective(user));
    }

    @ZSetCacheEvict(key = RedisInterface.USER_REDIS_USERNAME_KEY,score = "#id")
    //@CacheWithHash(name = RedisInterface.USER_REDIS_USERNAME_KEY,key = "#username",operation = HashOperation.REMOVE)
    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.USER_REDIS_ID_KEY,key = "#id"),
                    //@CacheEvict(value = RedisInterface.USER_REDIS_USERNAME_KEY,key = )
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_UPC_ID_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_TEL_KEY,allEntries = true)
            }
    )
    @PutMapping("/update_username/{id}")
    @ApiOperation(value = "修改用户名",httpMethod = "PUT",nickname = "updateUsername")
    public Result updateUsername(@PathVariable("id") @Min(1)
                                 @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int id,
                                 @RequestParam("username") @NotNull @ApiParam(value = "用户名",required = true,type = "string",example = "zhangsan") String username) {
        if(userService.checkExistByUsrename(username)!=0)
            return new Result<>("0","用户名已存在");
        User user=new User();
        user.setId(id);
        user.setUsername(username);
        user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(userService.updateByPrimaryKeySelective(user));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.USER_REDIS_ID_KEY,key = "#id"),
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_UPC_ID_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_TEL_KEY,allEntries = true)
            }
    )
    @PutMapping("/update_tel/{id}")
    @ApiOperation(value = "修改电话号码",httpMethod = "PUT",nickname = "updateTel")
    public Result updateTel(@PathVariable("id") @Min(1)
                                 @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int id,
                                 @RequestParam("tel") @NotNull @ApiParam(value = "用户电话",required = true,type = "string",example = "12345678") String tel) {
        if(userService.checkExistByTel(tel)!=0)
            return new Result<>("0","此电话号码以存在！");
        User user=new User();
        user.setId(id);
        user.setTel(tel);
        user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return new Result<>(userService.updateByPrimaryKeySelective(user));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.USER_REDIS_ID_KEY,key = "#id"),
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_UPC_ID_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_TEL_KEY,allEntries = true)
            }
    )
    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个用户接口",httpMethod = "DELETE",nickname = "deleteOneUser")
    public Result delete(@PathVariable("id") @Min(1)
                             @ApiParam(value = "用户IID", required = true, type = "integer", example = "1") int id) {

        return new Result<>(userService.delete(id));
    }

    @Caching(
            evict = {
                    @CacheEvict(value = RedisInterface.USER_REDIS_ID_KEY,key = "#id"),
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_UPC_ID_KEY,allEntries = true),
                    @CacheEvict(value = RedisInterface.COMBO_DAY_LIST_TEL_KEY,allEntries = true)
            }
    )
    @ApiOperation(value = "更新用户绑定谷歌验证接口",httpMethod = "PUT",nickname = "updateUserBinding")
    @PutMapping("/update_binding")
    public Result updateBinding(@RequestParam("id") @Min(1) @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int id,
                                @RequestParam("isBinding") @ApiParam(value = "绑定值",required = true,type = "integer",example = "1") int isBinding) {
        return new Result<>(userService.updateBinding(id,isBinding));
    }

    @ApiOperation(value = "通过手机更新密码接口",httpMethod = "PUT",nickname = "updatePasswordByTel")
    @PutMapping("/update_password_by_tel")
    public Result updatePasswordByTel(@RequestParam("tel") @ApiParam(value = "用户电话",required = true,type = "string",example = "123456") String tel,
                                      @RequestParam("password") @ApiParam(value = "密码",required = true,type = "string",example = "123456") String password) {
        User byTel = userService.getByTel(tel);
        if (byTel!=null) {

            Result result = new Result<>(userService.updatePasswordByTel(tel, new BCryptPasswordEncoder().encode(password)));
            userService.evictUser(byTel.getId());
            return result;
        }
        else
            return new Result<>(0,"用户不存在");

    }

    @GetMapping("/check_exist_by_id")
    public Result checkExistById(@RequestParam("id") @Min(1) @ApiParam(value = "用户ID",required = true,type = "integer",example = "1") int id) {
        return new Result<>(userService.checkExistById(id));
    }

    @ApiOperation(value = "获取多个用户信息接口",httpMethod = "GET",nickname = "getUserByIds")
    @GetMapping("/list_by_ids")
    public Map<Integer, User> listByIds(@RequestParam("ids") List<Integer> ids){

        return userService.getByIds(ids);
    }

    @PutMapping("/status")
    public Result updateStatusById(@RequestParam("id") Integer id, @RequestParam("status") Integer status) {
        Integer i = userService.updateStatusById(id, status);
        Result result;
        if (i > 0) {
            result = new Result<>(1,"success");
        }else {
            result = new Result<>(0,"failed") ;
        }
        return result;
    }
}