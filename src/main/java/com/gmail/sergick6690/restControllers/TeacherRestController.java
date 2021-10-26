package com.gmail.sergick6690.restControllers;

import com.gmail.sergick6690.ModelsCreator;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.TeacherForm;
import com.gmail.sergick6690.service.TeacherService;
import com.gmail.sergick6690.universityModels.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "dev/teachers", produces = {"application/xml", "application/json"})
public class TeacherRestController {
    private TeacherService teacherService;
    private ModelsCreator modelsCreator;

    @Autowired
    public TeacherRestController(TeacherService teacherService, ModelsCreator modelsCreator) {
        this.teacherService = teacherService;
        this.modelsCreator = modelsCreator;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Teacher addTeacher(@RequestBody TeacherForm teacherForm) throws ServiceException {
        Teacher teacher = modelsCreator.createNewTeacher(teacherForm);
        teacherService.add(teacher);
        return teacher;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Teacher> showAllTeachers() throws ServiceException {
        return teacherService.findAll();
    }

    @GetMapping("/{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    public Teacher showTeachersById(@PathVariable int teacherId) throws ServiceException {
        return teacherService.findById(teacherId);
    }

    @PutMapping("/assign/schedule")
    @ResponseStatus(HttpStatus.OK)
    public void assignSchedule(@RequestParam("teacherId") int teacherId, @RequestParam("scheduleId") int scheduleId) throws ServiceException {
        teacherService.assignSchedule(teacherId, scheduleId);
    }

    @PutMapping("remove/schedule")
    @ResponseStatus(HttpStatus.OK)
    public void removeSchedule(@RequestParam("teacherId") int teacherId) throws ServiceException {
        teacherService.removeSchedule(teacherId);
    }

    @PutMapping("change/schedule")
    @ResponseStatus(HttpStatus.OK)
    public void changeSchedule(@RequestParam("teacherId") int teacherId, @RequestParam("scheduleId") int scheduleId) throws ServiceException {
        teacherService.changeSchedule(teacherId, scheduleId);
    }

    @DeleteMapping("/{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    public void removeTeacher(@PathVariable int teacherId) throws ServiceException {
        teacherService.removeById(teacherId);
    }
}
