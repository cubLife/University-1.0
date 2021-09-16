package com.gmail.sergick6690.implementation;

import com.gmail.sergick6690.PropertyLoader;
import com.gmail.sergick6690.exceptions.RepositoryException;
import com.gmail.sergick6690.university.Schedule;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Properties;

@Repository
@Transactional(rollbackFor = RepositoryException.class)
public class JpaScheduleRepository implements com.gmail.sergick6690.Repository.ScheduleRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private Properties properties = new PropertyLoader("Queries/scheduleQueries.properties").loadProperty();
    private static final String FIND_ALL = "findAllSchedules";

    @Override
    public void add(Schedule schedule) throws RepositoryException {
        try {
            entityManager.persist(schedule);
        } catch (Exception e) {
            throw new RepositoryException("Can't add schedule - " + schedule, e);
        }
    }

    @Override
    public Schedule findById(int id) throws RepositoryException {
        try {
            Schedule schedule = entityManager.find(Schedule.class, id);
            if (schedule != null) {
                return schedule;
            } else throw new IllegalArgumentException("Schedule not found - " + id);
        } catch (Exception e) {
            throw new RepositoryException("Schedule not found - " + id, e);
        }
    }

    @Override
    public List<Schedule> findAll() throws RepositoryException {
        try {
            return entityManager.createQuery(properties.getProperty(FIND_ALL), Schedule.class).getResultList();
        } catch (Exception e) {
            throw new RepositoryException("Can't find any schedule", e);
        }
    }

    @Override
    public void removeById(int id) throws RepositoryException {
        try {
            Schedule schedule = findById(id);
            entityManager.remove(schedule);
        } catch (Exception e) {
            throw new RepositoryException("Can't remove schedule with id - " + id, e);
        }
    }
}