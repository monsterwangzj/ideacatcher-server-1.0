package com.softwisdom.ideacatcher.dao;

import com.softwisdom.ideacatcher.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUser();

    User findUserByUsernamenPassword(String username, String password);

}
