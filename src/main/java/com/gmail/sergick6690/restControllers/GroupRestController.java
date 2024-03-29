package com.gmail.sergick6690.restControllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.GroupForm;
import com.gmail.sergick6690.service.GroupService;
import com.gmail.sergick6690.universityModels.Group;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/groups", produces = {"application/xml", "application/json"})
public class GroupRestController {
    private GroupService groupService;

    public GroupRestController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new group")
    public Group addGroup(@RequestBody GroupForm groupForm) throws ServiceException {
        Group group = groupService.createNewGroup(groupForm);
        groupService.add(group);
        return group;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all groups")
    public List<Group> showAllGroups() throws ServiceException {
        return groupService.findAll();
    }

    @GetMapping("/{group-id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get group by id")
    public Group showById(@PathVariable("group-id") int groupId) throws ServiceException {
        return groupService.findById(groupId);
    }

    @GetMapping("/related/{cathedra-id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all groups related to cathedra")
    public List<Group> showGroupsRelatedToCathedra(@PathVariable("cathedra-id") int cathedraId) throws ServiceException {
        return groupService.findAllGroupsRelatedToCathedra(cathedraId);
    }

    @DeleteMapping("/{group-id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete group by id")
    public void deleteById(@PathVariable("group-id") int groupId) throws ServiceException {
        groupService.removeById(groupId);
    }
}
