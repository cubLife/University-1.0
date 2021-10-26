package com.gmail.sergick6690.restControllers;

import com.gmail.sergick6690.ModelsCreator;
import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.StudentForm;
import com.gmail.sergick6690.service.StudentService;
import com.gmail.sergick6690.universityModels.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "dev/students", produces = {"application/xml", "application/json"})
public class StudentRestController {
    private StudentService studentService;
    private ModelsCreator modelsCreator;

    @Autowired
    public StudentRestController(StudentService studentService, ModelsCreator modelsCreator) {
        this.studentService = studentService;
        this.modelsCreator = modelsCreator;
    }

    @GetMapping()
    public List<Student> showAllStudents() throws ServiceException {
        return studentService.findAll();
    }

    @GetMapping("/{studentId}")
    public Student showById(@PathVariable int studentId) throws ServiceException {
        return studentService.findById(studentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student add(@RequestBody StudentForm studentForm) throws ServiceException {
        Student student = modelsCreator.createNewStudent(studentForm);
        studentService.add(student);
        return student;
    }

    @DeleteMapping("/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable int studentId) throws ServiceException {
        studentService.removeById(studentId);
    }

    @PutMapping("/assign/group")
    @ResponseStatus(HttpStatus.OK)
    public void assignGroup(@RequestParam("studentId") int studentId, @RequestParam("groupId") int groupId) throws ServiceException {
        studentService.assignGroup(studentId, groupId);
    }

    @PutMapping("/remove/group")
    @ResponseStatus(HttpStatus.OK)
    public void removeFromGroup(@RequestParam("studentId") int studentId) throws ServiceException {
        studentService.removeFromGroup(studentId);
    }

    @PutMapping("/change/group")
    @ResponseStatus(HttpStatus.OK)
    public void changeGroup(@RequestParam("studentId") int studentId, @RequestParam("groupId") int groupId) throws ServiceException {
        studentService.changeGroup(studentId, groupId);
    }

    @PutMapping("/assign/course")
    @ResponseStatus(HttpStatus.OK)
    public void assignCourse(@RequestParam("studentId") int studentId, @RequestParam("course") int course) throws ServiceException {
        studentService.assignCourse(studentId, course);
    }

    @PutMapping("/remove/course")
    @ResponseStatus(HttpStatus.OK)
    public void removeFromCourse(@RequestParam("studentId") int studentId) throws ServiceException {
        studentService.removeFromCourse(studentId);
    }

    @PutMapping("/change/course")
    @ResponseStatus(HttpStatus.OK)
    public void changeCourse(@RequestParam("studentId") int studentId, @RequestParam("course") int course) throws ServiceException {
        studentService.changeCourse(studentId, course);
    }
}
