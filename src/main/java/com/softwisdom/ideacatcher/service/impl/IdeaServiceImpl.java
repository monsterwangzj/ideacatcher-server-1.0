package com.softwisdom.ideacatcher.service.impl;

import com.softwisdom.ideacatcher.dao.IdeaDao;
import com.softwisdom.ideacatcher.model.Idea;
import com.softwisdom.ideacatcher.service.IdeaService;

import java.util.List;

public class IdeaServiceImpl implements IdeaService {

    private IdeaDao ideaDao;

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

    public void setIdeaDao(IdeaDao ideaDao) {
        this.ideaDao = ideaDao;
    }
}
