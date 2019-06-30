package com.itheima.dao.impl;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.util.JdbcUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());

    /*注册用户*/
    @Override
    public int register(User user) {
        String sql = "INSERT INTO tab_user(uid,username,PASSWORD,NAME,birthday,sex,telephone,email, STATUS, CODE) VALUES (?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(), user.getStatus(), user.getCode()};
        return jdbcTemplate.update(sql, params);
    }

    /*激活用户*/
    @Override
    public int active(String code) {
        return jdbcTemplate.update("update tab_user set status = 'Y' where code = ?", code);
    }

    /*登录查询*/
    @Override
    public User login(String username, String password) {
        User user = null;
        try {
            user = jdbcTemplate.queryForObject("SELECT * FROM tab_user WHERE username = ? AND PASSWORD = ?", new BeanPropertyRowMapper<>(User.class), username, password);
        } catch (DataAccessException e) {
            System.out.println("找不到用户username=" + username + ",password=" + password);
        }
        return user;
    }
}
