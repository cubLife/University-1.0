package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.university.Group;

import java.sql.SQLException;
import java.util.List;

public interface GroupDAO {
    public void addGroup(Group group);

    public Group findGroupById(int id) throws SQLException;

    public List<Group> findAllGroups();

    public void removeGroupById(int id);

    public List<Group> findAllGroupsRelatedToCathedra(int id);
}
