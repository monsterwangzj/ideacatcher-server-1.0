package com.softwisdom.ideacatcher.dao.impl;

import com.softwisdom.ideacatcher.dao.IdeaDao;
import com.softwisdom.ideacatcher.model.Idea;
import org.hibernate.SessionFactory;

import java.util.List;

public class IdeaDaoImpl implements IdeaDao {

    private SessionFactory sessionFactory;

    public Idea save(Idea idea) {
        return null;
    }

    public Idea update(Idea idea) {
        return null;
    }

    public boolean delete(Long id) {
        return false;
    }

    public Idea findById(Long id) {
        return null;
    }

    public List<Idea> findAll() {
        return null;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
