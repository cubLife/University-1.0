package com.gmail.sergick6690.restControllers;

import com.gmail.sergick6690.ModelsCreator;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.GroupForm;
import com.gmail.sergick6690.service.GroupService;
import com.gmail.sergick6690.universityModels.Group;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "dev/groups", produces = {"application/xml", "application/json"})
public class GroupRestController {
    private GroupService groupService;
    private ModelsCreator modelsCreator;

    public GroupRestController(GroupService groupService, ModelsCreator modelsCreator) {
        this.groupService = groupService;
        this.modelsCreator = modelsCreator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Group addGroup(@RequestBody GroupForm groupForm) throws ServiceException {
        Group group = modelsCreator.createNewGroup(groupForm);
        groupService.add(group);
        return group;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Group> showAllGroups() throws ServiceException {
        return groupService.findAll();
    }

    @GetMapping("/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public Group showById(@PathVariable int groupId) throws ServiceException {
        return groupService.findById(groupId);
    }

    @GetMapping("/related/{cathedraId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Group> showGroupsRelatedToCathedra(@PathVariable int cathedraId) throws ServiceException {
        return groupService.findAllGroupsRelatedToCathedra(cathedraId);
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable int groupId) throws ServiceException {
        groupService.removeById(groupId);
    }
}
