package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.AdminMapper;
import cn.bosenkeji.service.AdminService;
import cn.bosenkeji.vo.Admin;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return Optional.empty();
    }

    @Override
    public boolean add(Admin admin) {
        return false;
    }

    @Override
    public boolean update(Admin admin) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
