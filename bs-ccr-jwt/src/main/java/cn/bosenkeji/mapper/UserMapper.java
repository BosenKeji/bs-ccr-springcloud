package cn.bosenkeji.mapper;

import cn.bosenkeji.vo.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName UserMapper
 * @Description TODO
 * @Author hjh
 * @Date 2019-08-03 19:39
 * @Version 1.0
 */

@Repository
public class UserMapper {

    private List<User> userList = new ArrayList<>();

    public UserMapper() {
        for (int i=0;i<5;i++) {
            User u = new User();
            u.setId(i);
            StringBuffer sbf = new StringBuffer();
            sbf.append(i);
            sbf.append(i);
            sbf.append(i);
            u.setUsername(sbf.toString());
            u.setPassword(sbf.toString());
            u.setTel(sbf.toString());
            u.setStatus(1);
        }
    }

    public User findUserByName(String name) {
        Optional<User> user = userList.stream().filter((u) -> !u.getUsername().equals(name)).findFirst();
        return user.get();
    }

    public List<User> findAll() {
        return userList;
    }
}
