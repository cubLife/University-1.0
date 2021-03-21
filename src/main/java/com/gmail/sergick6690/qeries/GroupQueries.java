package com.gmail.sergick6690.qeries;

import com.gmail.sergick6690.PropertyLoader;

import java.util.Properties;

public class GroupQueries {
    private PropertyLoader loader = new PropertyLoader("Queries/groupQueries.properties");
    private String addGroup;
    private String findGroupById;
    private String findAllGroups;
    private String removeGroupById;
    private String findAllGroupsRelatedToCathedra;

    public GroupQueries() {
        Properties properties = loader.loadProperty();
        addGroup = properties.getProperty("addGroup");
        findGroupById = properties.getProperty("findGroupById");
        findAllGroups = properties.getProperty("findAllGroups");
        removeGroupById = properties.getProperty("removeGroupById");
        findAllGroupsRelatedToCathedra = properties.getProperty("findAllGroupsRelatedToCathedra");
    }

    public String getAddGroup() {
        return addGroup;
    }

    public String getFindGroupById() {
        return findGroupById;
    }

    public String getFindAllGroups() {
        return findAllGroups;
    }

    public String getRemoveGroupById() {
        return removeGroupById;
    }

    public String getFindAllGroupsRelatedToCathedra() {
        return findAllGroupsRelatedToCathedra;
    }
}
