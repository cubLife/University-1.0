package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.CrudMethods;
import com.gmail.sergick6690.implementation.JdbcCathedraDAO;
import com.gmail.sergick6690.university.Cathedra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CathedraService implements CrudMethods<Cathedra> {
    private JdbcCathedraDAO cathedraDAO;

    @Autowired
    public CathedraService(JdbcCathedraDAO cathedraDAO) {
        this.cathedraDAO = cathedraDAO;
    }

    @Override
    public void add(Cathedra cathedra) {
        if (cathedra == null) {
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        cathedraDAO.add(cathedra);

    }

    @Override
    public Cathedra findById(int id) {
        return cathedraDAO.findById(id);
    }

    @Override
    public List<Cathedra> findAll() {
        return cathedraDAO.findAll();
    }

    @Override
    public void removeById(int id) {
        cathedraDAO.removeById(id);

    }
}