package cn.bosenkeji.service.Impl;

import cn.bosenkeji.mapper.AdminMapper;
import cn.bosenkeji.service.AdminService;
import cn.bosenkeji.vo.Admin;
import cn.bosenkeji.vo.Coin;
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
    @Override
    public List<Admin> list() {
        return null;
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

//    @Autowired
//    private AdminMapper adminMapper;
//
//    @Override
//    public List<Admin> list() {
//        return adminMapper.findAll();
//    }
//
//    @Override
//    public Optional<Coin> get(int id) {
//        return Optional.ofNullable(coinMapper.selectByPrimaryKey(id)) ;
//    }
//
//    @Override
//    public boolean add(Coin coin) {
//        return coinMapper.insert(coin)==1;
//    }
//
//    @Override
//    public boolean update(Coin coin) {
//        return coinMapper.updateByPrimaryKeySelective(coin)==1;
//    }
//
//    @Override
//    public boolean delete(int id) {
//        return coinMapper.deleteByPrimaryKey(id)==1;
//    }
}
