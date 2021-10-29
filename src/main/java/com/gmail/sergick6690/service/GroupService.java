package com.gmail.sergick6690.service;

import com.gmail.sergick6690.Repository.CathedraRepository;
import com.gmail.sergick6690.Repository.GroupRepository;
import com.gmail.sergick6690.Repository.ScheduleRepository;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.GroupForm;
import com.gmail.sergick6690.universityModels.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.String.format;

@Service
public class GroupService {
    private GroupRepository groupRepository;
    private ScheduleRepository scheduleRepository;
    private CathedraRepository cathedraRepository;
    private static final Logger ERROR = LoggerFactory.getLogger("com.gmail.sergick6690.error");
    private static final Logger DEBUG = LoggerFactory.getLogger("com.gmail.sergick6690.debug");

    @Autowired
    public GroupService(GroupRepository groupRepository, ScheduleRepository scheduleRepository, CathedraRepository cathedraRepository) {
        this.groupRepository = groupRepository;
        this.scheduleRepository = scheduleRepository;
        this.cathedraRepository = cathedraRepository;
    }

    public void add(Group group) throws ServiceException {
        if (group == null) {
            ERROR.error("Input parameter was null", new IllegalArgumentException("Input parameter can't be null"));
            throw new IllegalArgumentException("Input parameter can't be null");
        }
        try {
            groupRepository.save(group);
            DEBUG.debug((format("New group - %s was added", group.toString())));
        } catch (NumberFormatException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't add group - " + group + e, e);
        }
    }

    public Group findById(int id) throws ServiceException {
        try {
            Group group = groupRepository.findById(id).get();
            DEBUG.debug(format("Group with id - %d was returned", id));
            return group;
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Group not found - " + id + e, e);
        }
    }

    public List<Group> findAll() throws ServiceException {
        try {
            List<Group> groupList = groupRepository.findAll();
            DEBUG.debug("All groups was returned");
            return groupList;
        } catch (NullPointerException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't find any group " + e, e);
        }
    }

    public void removeById(int id) throws ServiceException {
        try {
            groupRepository.delete(this.findById(id));
            DEBUG.debug(format("Group with id - %d is removed", id));
        } catch (NoSuchElementException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Can't remove group with id - " + id + e, e);
        }
    }

    public List<Group> findAllGroupsRelatedToCathedra(int id) throws ServiceException {
        try {
            List<Group> groupList = groupRepository.findAllByCathedra_Id(id);
            DEBUG.debug(String.format("Was returned %d related to cathedra with id - %d", groupList.size(), id));
            return groupList;
        } catch (NullPointerException e) {
            ERROR.error(e.getMessage(), e);
            throw new ServiceException("Cant find any groups related to cathedra with cathedta id - " + id + e, e);
        }
    }

    public Group createNewGroup(GroupForm groupForm){
        Group group = new Group();
        group.setName(groupForm.getName());
        group.setSchedule(scheduleRepository.findById(groupForm.getScheduleId()).get());
        group.setCathedra(cathedraRepository.findById(groupForm.getCathedraId()).get());
        return group;
    }
}
