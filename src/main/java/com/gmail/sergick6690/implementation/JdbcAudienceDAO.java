package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.AudienceDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Audience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Properties;

@Repository
public class JdbcAudienceDAO implements AudienceDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/audienceQueries.properties").loadProperty();
    private static final String ADD = "addAudience";
    private static final String FIND_BY_ID = "findAudienceById";
    private static final String FIND_ALL = "findAllAudience";
    private static final String REMOVE = "removeAudience";

    @Autowired
    public JdbcAudienceDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Audience audience) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(ADD), audience.getNumber());
        } catch (Exception e) {
            throw new DaoException("Cant't add audience" + e, e);

        }
    }

    @Override
    public Audience findById(int id) throws DaoException {
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<>(Audience.class), id)
                .stream().findAny().orElseThrow(() -> new DaoException("Audience not found - " + id));
    }

    @Override
    public List<Audience> findAll() throws DaoException {
        try {
            return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Audience.class));
        } catch (Exception e) {
            throw new DaoException("Can't find any audiences", e);
        }
    }

    @Override
    public void removeById(int id) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(REMOVE), id);
        } catch (Exception e) {
            throw new DaoException("Can't remove audience with id - " + id, e);
        }
    }
}