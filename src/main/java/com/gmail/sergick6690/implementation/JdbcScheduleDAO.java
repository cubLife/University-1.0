package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Properties;

@Repository
public class JdbcScheduleDAO implements ScheduleDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/scheduleQueries.properties").loadProperty();
    private static final String ADD = "addSchedule";
    private static final String FIND_BY_ID = "findScheduleByID";
    private static final String FIND_ALL = "findAllSchedules";
    private static final String REMOVE = "removeScheduleById";

    @Autowired
    public JdbcScheduleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Schedule schedule) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(ADD), schedule.getName());
        } catch (Exception e) {
            throw new DaoException("Can't add schedule - " + schedule, e);
        }
    }

    @Override
    public Schedule findById(int id) throws DaoException {
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<Schedule>(Schedule.class), id)
                .stream().findAny().orElseThrow(() -> new DaoException("Schedule not found - " + id));
    }

    @Override
    public List<Schedule> findAll() throws DaoException {
        try {
            return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Schedule.class));
        } catch (Exception e) {
            throw new DaoException("Can't find any schedule", e);
        }
    }

    @Override
    public void removeById(int id) throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(REMOVE), id);
        } catch (Exception e) {
            throw new DaoException("Can't remove schedule with id - " + id, e);
        }
    }
}