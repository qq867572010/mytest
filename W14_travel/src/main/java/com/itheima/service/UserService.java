package com.itheima.service;

import com.itheima.domain.User;

public interface UserService {
    boolean register(User user) throws Exception;

    boolean active(String code);

    User login(String username, String password) throws Exception;
}
