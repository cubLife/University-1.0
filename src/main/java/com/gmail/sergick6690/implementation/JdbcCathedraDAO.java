package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.CathedraDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Cathedra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Properties;

@Repository
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
    public void add(Cathedra cathedra) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(ADD), cathedra.getName(), cathedra.getFacultyId());
        } catch (Exception e) {
            throw new DaoException("Cant add cathedra - " + cathedra, e);
        }
    }

    @Override
    public void removeById(int id) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(REMOVE), id);
        } catch (Exception e) {
            throw new DaoException("Cant remove cathedra with id - " + id, e);
        }
    }

    @Override
    public Cathedra findById(int id) throws DaoException {
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<>(Cathedra.class), id)
                .stream().findAny().orElseThrow(() -> new DaoException("Cathedra not found - " + id));
    }

    @Override
    public List<Cathedra> findAll() throws DaoException {
        try {
            return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Cathedra.class));
        } catch (Exception e) {
            throw new DaoException("Can't find any cathedras", e);
        }
    }
}
