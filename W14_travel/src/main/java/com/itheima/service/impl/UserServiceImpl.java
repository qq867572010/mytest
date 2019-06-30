package com.itheima.service.impl;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.util.Md5Utils;

public class UserServiceImpl implements UserService {

    private UserDao dao = new UserDaoImpl();

    /*注册用户*/
    @Override
    public boolean register(User user) throws Exception {
        //给密码加密
        String s = Md5Utils.encodeByMd5(user.getPassword());
        user.setPassword(s);

        int count = dao.register(user);
        return count > 0;
    }

    /*激活用户*/
    @Override
    public boolean active(String code) {
        int count = dao.active(code);
        return count > 0;
    }

    /*登录查询*/
    @Override
    public User login(String username, String password) throws Exception {
        //将密码转密
        password = Md5Utils.encodeByMd5(password);
        User user = dao.login(username,password);
        return user;
    }


}
