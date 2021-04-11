package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.CrudMethods;
import com.gmail.sergick6690.implementation.JdbcGroupDAO;
import com.gmail.sergick6690.university.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService implements CrudMethods<Group> {
    private JdbcGroupDAO groupDAO;

    @Autowired
    public GroupService(JdbcGroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @Override
    public void add(Group group) {
        if (group == null) {
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        groupDAO.add(group);
    }

    @Override
    public Group findById(int id) {
        return groupDAO.findById(id);
    }

    @Override
    public List<Group> findAll() {
        return groupDAO.findAll();
    }

    @Override
    public void removeById(int id) {
        groupDAO.removeById(id);
    }

    public List<Group> findAllGroupsRelatedToCathedra(int id) {
        return groupDAO.findAllGroupsRelatedToCathedra(id);
    }
}
