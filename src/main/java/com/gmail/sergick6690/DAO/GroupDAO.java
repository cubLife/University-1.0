package com.gmail.sergick6690.DAO;

import com.gmail.sergick6690.Group;

import java.util.List;

public interface GroupDAO {
    public void addGroup(Group group);

    public Group findGroupById(int id);

    public List<Group> findAllGroups();

    public void removeGroupById(int id);

    public List<Group> findAllGroupsRelatedToCathedra(int id);
}
