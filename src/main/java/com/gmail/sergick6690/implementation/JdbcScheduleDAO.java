package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.university.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@Component
public class JdbcScheduleDAO implements ScheduleDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/scheduleQueries.properties").loadProperty();

    @Autowired
    public JdbcScheduleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Schedule schedule) {
        jdbcTemplate.update(properties.getProperty("addSchedule"), schedule.getName());
    }

    public Schedule findById(int id) throws SQLException {
        return jdbcTemplate.query(properties.getProperty("findScheduleByID"), new BeanPropertyRowMapper<>(Schedule.class), id)
                .stream().findAny().orElseThrow(() -> new SQLException("Schedule not found - " + id));
    }

    @Override
    public List<Schedule> findAll() {
        return jdbcTemplate.query(properties.getProperty("findAllSchedules"), new BeanPropertyRowMapper<>(Schedule.class));
    }

    @Override
    public void removeById(int id) {
        jdbcTemplate.update(properties.getProperty("removeScheduleById"), id);
    }
}
