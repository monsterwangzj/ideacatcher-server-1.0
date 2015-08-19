package com.softwisdom.ideacatcher.service;

import com.softwisdom.ideacatcher.model.User;

public interface UserService {

    int userCount();

    User findUserByUsernamenPassword(String username, String password);

}
