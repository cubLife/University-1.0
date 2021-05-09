package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.DAO.TeacherDAO;
import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.DaoException;
import com.gmail.sergick6690.university.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Properties;

@Repository
public class JdbcTeacherDAO implements TeacherDAO {
    private JdbcTemplate jdbcTemplate;
    private Properties properties = new PropertyLoader("Queries/teacherQueries.properties").loadProperty();
    private static final String ADD = "addTeacher";
    private static final String FIND_BY_ID = "findTeacherById";
    private static final String FIND_ALL = "findAllTeacher";
    private static final String REMOVE = "removeTeacherById";
    private static final String FIND_WITH_EQUAL_DEGREE = "findAllTeachersWithEqualDegree";
    private static final String REMOVE_SCHEDULE = "removeSchedule";
    private static final String ASSIGN_SCHEDULE = "assignSchedule";

    @Autowired
    public JdbcTeacherDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Teacher teacher)throws DaoException {
        try {
            jdbcTemplate.update(properties.getProperty(ADD), teacher.getFirstName(), teacher.getLastNAme(), teacher.getSex(),
                    teacher.getAge(), teacher.getDegree(), teacher.getScheduleId());
        }catch (Exception e){
            throw new DaoException("Can't add teacher - "+teacher,e);
        }
    }

    @Override
    public void removeById(int id) throws DaoException{
        try {
            jdbcTemplate.update(properties.getProperty(REMOVE), id);
        }catch (Exception e){
            throw new DaoException("Can't remove teacher with id - "+id,e);
        }
    }

    @Override
    public Teacher findById(int id) throws DaoException{
        return jdbcTemplate.query(properties.getProperty(FIND_BY_ID), new BeanPropertyRowMapper<>(Teacher.class), id)
                .stream().findAny().orElseThrow(() -> new  DaoException("Teacher not found - " + id));
    }

    @Override
    public List<Teacher> findAll()throws DaoException {
        try {
            return jdbcTemplate.query(properties.getProperty(FIND_ALL), new BeanPropertyRowMapper<>(Teacher.class));
        }catch (Exception e){
            throw new DaoException("Can't find any teacher",e);
        }

    }

    @Override
    public Integer findTeachersCountWithEqualDegree(String degree) throws DaoException{
        try {
            return jdbcTemplate.queryForObject(properties.getProperty(FIND_WITH_EQUAL_DEGREE), Integer.class, degree);
        }catch (Exception e){
            throw new DaoException("Can't find any teacher with degree - "+degree,e);
        }
    }

    @Override
    public void removeSchedule(int teacherId) throws DaoException{
        try {
            jdbcTemplate.update(properties.getProperty(REMOVE_SCHEDULE), teacherId);
        }catch (Exception e){
            throw new DaoException("Can't remove schedule for teacher with id - "+teacherId,e);
        }
    }

    @Override
    public void assignSchedule(int teacherId, int scheduleId) throws DaoException{
        try {
            jdbcTemplate.update(properties.getProperty(ASSIGN_SCHEDULE), scheduleId, teacherId);
        }catch (Exception e){
            throw new DaoException("Cant assign schedule with id - "+scheduleId+"for teacher with id - "+teacherId,e);
        }
    }
}