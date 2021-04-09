package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.ScheduleDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.university.Schedule;
import org.apache.maven.surefire.shared.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
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
    public void add(Schedule schedule) {
        jdbcTemplate.update(properties.getProperty(ADD), schedule.getName());
    }

    public Schedule findById(int id) throws NotImplementedException {
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<>(Schedule.class), id)
                .stream().findAny().orElseThrow(() -> new NotImplementedException("Schedule not found - " + id));
    }

    @Override
    public List<Schedule> findAll() {
        return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Schedule.class));
    }

    @Override
    public void removeById(int id) {
        jdbcTemplate.update(properties.getProperty(REMOVE), id);
    }
}
