package cn.bosenkeji.controller;

import cn.bosenkeji.exception.AddException;
import cn.bosenkeji.exception.DeleteException;
import cn.bosenkeji.exception.NotFoundException;
import cn.bosenkeji.exception.UpdateException;
import cn.bosenkeji.exception.enums.UserEnum;
import cn.bosenkeji.service.UserService;
import cn.bosenkeji.util.Result;
import cn.bosenkeji.vo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
@Api(tags = "User 用户相关接口",value = "提供用户相关的 Rest API接口")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @ApiOperation(value = "获取单个用户接口", httpMethod = "GET", nickname = "getOneUser")
    public User get(@PathVariable("id") @Min(1) @ApiParam(value = "用户IID", required = true, type = "integer", example = "1") int id) {
        return userService.get(id).orElseThrow(() -> new NotFoundException(UserEnum.NAME));
    }

    @GetMapping("/get_by_username")
    @ApiOperation(value = "通过用户名获取单个用户接口", httpMethod = "GET", nickname = "getOneUserByUsername")
    public User getByUsername(@RequestParam("username") @NotNull @ApiParam(value = "用户名", required = true, type = "string", example = "zhangsan") String username) {
        return userService.getByUsername(username).orElseThrow(() -> new NotFoundException(UserEnum.NAME));
    }

    @PostMapping("/")
    @ApiOperation(value = "添加单个用户接口", httpMethod = "POST", nickname = "addOneUser")
    public Result add(@RequestBody @NotNull @ApiParam(value = "用户实体", required = true, type = "string") User user) {

        userService.checkExistByUsrename(user.getUsername())
                .filter((value) -> value == 0)
                .orElseThrow(() -> new AddException(UserEnum.NAME));

        user.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        user.setStatus(1);

        return new Result(userService.add(user)
                .filter((value) -> value >= 1)
                .orElseThrow(() -> new AddException(UserEnum.NAME)));
    }

    @PutMapping("/")
    @ApiOperation(value = "更新用户接口",httpMethod = "PUT",nickname = "updateUser")
    public Result update(@RequestBody @NotNull @ApiParam(value = "用户实体", required = true, type = "string") User user) {

        userService.checkExistByUsrename(user.getUsername())
                .filter((value)->value==0)
                .orElseThrow(()->new UpdateException(UserEnum.NAME));

        user.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        return new Result(userService.update(user)
                .filter((value) -> value >= 1)
                .orElseThrow(() -> new UpdateException(UserEnum.NAME)));

    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除单个用户接口",httpMethod = "DELETE",nickname = "deleteOneUser")
    public Result delete(@PathVariable("id") @Min(1) @ApiParam(value = "用户IID", required = true, type = "integer", example = "1") int id) {

        return new Result(userService.delete(id).filter((value)->value>=1).orElseThrow(()->new DeleteException(UserEnum.NAME)));
    }

}