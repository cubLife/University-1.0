package com.gmail.sergick6690.qeries;

import com.gmail.sergick6690.PropertyLoader;

import java.util.Properties;

public class ScheduleQueries {
    private PropertyLoader propertyLoader = new PropertyLoader("Queries/scheduleQueries.properties");
    private String addSchedule;
    private String findScheduleByID;
    private String findAllSchedules;
    private String removeScheduleById;

    public ScheduleQueries() {
        Properties properties = propertyLoader.loadProperty();
        addSchedule = properties.getProperty("addSchedule");
        findScheduleByID = properties.getProperty("findScheduleByID");
        findAllSchedules = properties.getProperty("findAllSchedules");
        removeScheduleById = properties.getProperty("removeScheduleById");
    }

    public PropertyLoader getPropertyLoader() {
        return propertyLoader;
    }

    public String getAddSchedule() {
        return addSchedule;
    }

    public String getFindScheduleByID() {
        return findScheduleByID;
    }

    public String getFindAllSchedules() {
        return findAllSchedules;
    }

    public String getRemoveScheduleById() {
        return removeScheduleById;
    }
}
