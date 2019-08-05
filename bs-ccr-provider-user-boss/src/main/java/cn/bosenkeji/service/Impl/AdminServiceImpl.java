package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.AdminMapper;
import cn.bosenkeji.service.AdminService;
import cn.bosenkeji.vo.Admin;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName CoinServiceImpl
 * @Description 货币实现类
 * @Author Yu XueWen
 * @Email 8586826@qq.com
 * @Versio V1.0
**/
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<Admin> list() {
        return adminMapper.findAll();
    }

    @Override
    public PageInfo listByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        return new PageInfo(adminMapper.findAll());
    }

    @Override
    public Optional<Admin> get(int id) {
        return Optional.of(adminMapper.selectByPrimaryKey(id));
    }

    @Override
    public Optional<Admin> selectByAccount(String account) {
        return Optional.ofNullable(adminMapper.selectByAccount(account));
    }

    @Override
    public Optional<Integer> add(Admin admin) {
        admin.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        admin.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(adminMapper.insert(admin));
    }

    @Override
    public Optional<Integer> update(Admin admin) {
        admin.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        admin.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(adminMapper.updateByPrimaryKeySelective(admin));
    }

    @Override
    public Optional<Integer> delete(int id) {
        return Optional.of(adminMapper.deleteByPrimaryKey(id));
    }
}
