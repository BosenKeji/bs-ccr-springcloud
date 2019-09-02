package cn.bosenkeji.service.impl;

import cn.bosenkeji.vo.Admin;
import cn.bosenkeji.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomJdbcUserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String USER_BASIC_COLUMN_LIST = " username, tel, password, status, is_binding ";
    private static final String ADMIN_BASIC_COLUMN_LIST = " account, password, status ";

    private static final String GET_ONE_USER_BY_TEL_SQL = "select" + USER_BASIC_COLUMN_LIST + "from user where tel = ?";
    private static final String GET_ONE_ADMIN_BY_ACCOUNT_SQL = "select"+ ADMIN_BASIC_COLUMN_LIST + "from admin where account = ?";

    Optional<User> getOneUserByTel(String tel) {

        RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
        return Optional.ofNullable(jdbcTemplate.queryForObject(GET_ONE_USER_BY_TEL_SQL, rowMapper, tel));
    }

    Optional<Admin> getOneAdminByAccount(String account) {

        RowMapper<Admin> rowMapper = new BeanPropertyRowMapper<>(Admin.class);
        return Optional.ofNullable(jdbcTemplate.queryForObject(GET_ONE_ADMIN_BY_ACCOUNT_SQL,rowMapper,account));
    }

}
