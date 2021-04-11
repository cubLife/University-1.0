package com.gmail.sergick6690.service;

import com.gmail.sergick6690.DAO.CrudMethods;
import com.gmail.sergick6690.implementation.JdbcAudienceDAO;
import com.gmail.sergick6690.university.Audience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AudienceService implements CrudMethods<Audience> {
    private JdbcAudienceDAO audienceDAO;

    @Autowired
    public AudienceService(JdbcAudienceDAO audienceDAO) {
        this.audienceDAO = audienceDAO;
    }

    @Override
    public void add(Audience audience) {
        if (audience == null) {
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        audienceDAO.add(audience);
    }

    @Override
    public Audience findById(int id) {
        return audienceDAO.findById(id);
    }

    @Override
    public List<Audience> findAll() {
        return audienceDAO.findAll();
    }

    @Override
    public void removeById(int id) {
        audienceDAO.removeById(id);
    }
}
