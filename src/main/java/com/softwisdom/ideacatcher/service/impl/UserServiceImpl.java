package com.softwisdom.ideacatcher.service.impl;

import com.softwisdom.ideacatcher.dao.UserDao;
import com.softwisdom.ideacatcher.model.User;
import com.softwisdom.ideacatcher.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public int userCount() {
        return userDao.getAllUser().size();
    }

    public User findUserByUsernamenPassword(String username, String password) {
        return userDao.findUserByUsernamenPassword(username, password);
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao usersDao) {
        this.userDao = usersDao;
    }

}
