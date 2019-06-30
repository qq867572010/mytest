package com.itheima.dao;

import com.itheima.domain.User;

public interface UserDao {
    int register(User user);

    int active(String code);

    User login(String username, String password);
}
