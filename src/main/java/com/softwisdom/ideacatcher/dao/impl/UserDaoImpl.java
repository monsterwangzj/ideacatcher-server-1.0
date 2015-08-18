package com.softwisdom.ideacatcher.dao.impl;

import com.softwisdom.ideacatcher.dao.UserDao;
import com.softwisdom.ideacatcher.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;

    public List<User> getAllUser() {
        String hsql = "from Users";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(hsql);

        return query.list();
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
