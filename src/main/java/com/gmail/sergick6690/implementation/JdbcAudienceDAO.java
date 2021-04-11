package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.AudienceDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.university.Audience;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
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
    public void add(Audience audience) {
        jdbcTemplate.update(properties.getProperty(ADD), audience.getNumber());
    }

    @Override
    public Audience findById(int id) throws NotImplementedException {
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<>(Audience.class), id)
                .stream().findAny().orElseThrow(() -> new NotImplementedException("Audience not found - " + id));
    }

    @Override
    public List<Audience> findAll() {
        return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Audience.class));
    }

    @Override
    public void removeById(int id) {
        jdbcTemplate.update(properties.getProperty(REMOVE), id);
    }
}
