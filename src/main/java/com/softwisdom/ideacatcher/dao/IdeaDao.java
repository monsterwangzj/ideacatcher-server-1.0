package com.softwisdom.ideacatcher.dao;

import com.softwisdom.ideacatcher.model.Idea;

import java.util.List;

public interface IdeaDao {

    Idea save(Idea idea);

    Idea update(Idea idea);

    boolean delete(Long id);

    Idea findById(Long id);

    List<Idea> findAll();

}
