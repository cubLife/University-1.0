package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.university.Cathedra;
import com.gmail.sergick6690.DAO.CathedraDAO;
import com.gmail.sergick6690.PropertyLoader;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
public class JdbcCathedraDAO implements CathedraDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/cathedraQueries.properties").loadProperty();
    private static final String ADD = "addCathedra";
    private static final String FIND_BY_ID = "findCathedraByID";
    private static final String FIND_ALL = "findAll";
    private static final String REMOVE = "removeCathedra";

    @Autowired
    public JdbcCathedraDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Cathedra cathedra) {
        jdbcTemplate.update(properties.getProperty(ADD), cathedra.getName());
    }

    @Override
    public void removeById(int id) {
        jdbcTemplate.update(properties.getProperty(REMOVE), id);
    }

    @Override
    public Cathedra findById(int id) throws NotImplementedException {
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<>(Cathedra.class), id)
                .stream().findAny().orElseThrow(() -> new NotImplementedException("Cathedra not found - " + id));
    }

    @Override
    public List<Cathedra> findAll() {
        return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Cathedra.class));
    }
}
