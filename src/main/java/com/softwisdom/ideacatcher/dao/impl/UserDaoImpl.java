package com.softwisdom.ideacatcher.dao.impl;

import com.softwisdom.ideacatcher.dao.UserDao;
import com.softwisdom.ideacatcher.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserDaoImpl implements UserDao {
    private static Logger logger = LogManager.getLogger(UserDaoImpl.class.getName());

    private SessionFactory sessionFactory;

    public List<User> getAllUser() {
        String hsql = "from Users";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);

        return query.list();
    }

    public User findUserByUsernamenPassword(String username, String password) {
        String hsql = "from user as u where u.username = :username and u.password = :password";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);
        query.setParameter("username", username);
        query.setParameter("password", password);
        List<User> userList = query.list();
        if (CollectionUtils.isEmpty(userList)) {
            return null;
        } else {
            if (userList.size() > 1) {
                logger.error("username " + username + ", password " + password + " 对应" + userList.size() + "个用户，存在账号风险，请立即处理");
            }
            return userList.get(0);
        }
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
