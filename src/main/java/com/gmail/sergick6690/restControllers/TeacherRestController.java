package com.gmail.sergick6690.restControllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.TeacherForm;
import com.gmail.sergick6690.service.TeacherService;
import com.gmail.sergick6690.universityModels.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/teachers", produces = {"application/xml", "application/json"})
public class TeacherRestController {
    private TeacherService teacherService;

    @Autowired
    public TeacherRestController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Teacher addTeacher(@RequestBody TeacherForm teacherForm) throws ServiceException {
        Teacher teacher = teacherService.createNewTeacher(teacherForm);
        teacherService.add(teacher);
        return teacher;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Teacher> showAllTeachers() throws ServiceException {
        return teacherService.findAll();
    }

    @GetMapping("/{teacher-id}")
    @ResponseStatus(HttpStatus.OK)
    public Teacher showTeachersById(@PathVariable("teacher-id") int teacherId) throws ServiceException {
        return teacherService.findById(teacherId);
    }

    @PutMapping("/assign/schedule")
    @ResponseStatus(HttpStatus.OK)
    public void assignSchedule(@RequestParam("teacher-id") int teacherId, @RequestParam("schedule-id") int scheduleId) throws ServiceException {
        teacherService.assignSchedule(teacherId, scheduleId);
    }

    @PutMapping("remove/schedule")
    @ResponseStatus(HttpStatus.OK)
    public void removeSchedule(@RequestParam("teacher-id") int teacherId) throws ServiceException {
        teacherService.removeSchedule(teacherId);
    }

    @PutMapping("change/schedule")
    @ResponseStatus(HttpStatus.OK)
    public void changeSchedule(@RequestParam("teacher-id") int teacherId, @RequestParam("schedule-id") int scheduleId) throws ServiceException {
        teacherService.changeSchedule(teacherId, scheduleId);
    }

    @DeleteMapping("/{teacher-id}")
    @ResponseStatus(HttpStatus.OK)
    public void removeTeacher(@PathVariable("teacher-id") int teacherId) throws ServiceException {
        teacherService.removeById(teacherId);
    }
}
