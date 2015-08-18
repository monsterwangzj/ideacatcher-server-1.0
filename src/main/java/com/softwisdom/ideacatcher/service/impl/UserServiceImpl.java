package com.softwisdom.ideacatcher.service.impl;

import com.softwisdom.ideacatcher.dao.UserDao;
import com.softwisdom.ideacatcher.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public int userCount() {
        return userDao.getAllUser().size();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao usersDao) {
        this.userDao = usersDao;
    }

}
