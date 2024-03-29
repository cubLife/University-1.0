package com.gmail.sergick6690.restControllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.StudentForm;
import com.gmail.sergick6690.service.StudentService;
import com.gmail.sergick6690.universityModels.Student;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/students", produces = {"application/xml", "application/json"})
public class StudentRestController {
    private StudentService studentService;

    @Autowired
    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/list")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Get all students")
    public List<Student> showAllStudents() throws ServiceException {
        return studentService.findAll();
    }

    @GetMapping("/{student-id}")
    @ResponseStatus(value = HttpStatus.OK)
    @Operation(summary = "Get student by id")
    public Student showById(@PathVariable("student-id") int studentId) throws ServiceException {
        return studentService.findById(studentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new student")
    public Student add(@RequestBody StudentForm studentForm) throws ServiceException {
        Student student = studentService.createNewStudent(studentForm);
        studentService.add(student);
        return student;
    }

    @DeleteMapping("/{student-id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete student by id")
    public void delete(@PathVariable("student-id") int studentId) throws ServiceException {
        studentService.removeById(studentId);
    }

    @PutMapping("/assign/group")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Assign group for student")
    public void assignGroup(@RequestParam("student-id") int studentId, @RequestParam("group-id") int groupId) throws ServiceException {
        studentService.assignGroup(studentId, groupId);
    }

    @PutMapping("/remove/group")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Remove student from group")
    public void removeFromGroup(@RequestParam("student-id") int studentId) throws ServiceException {
        studentService.removeFromGroup(studentId);
    }

    @PutMapping("/change/group")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change group for student")
    public void changeGroup(@RequestParam("student-id") int studentId, @RequestParam("group-id") int groupId) throws ServiceException {
        studentService.changeGroup(studentId, groupId);
    }

    @PutMapping("/assign/course")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Assign course for student ")
    public void assignCourse(@RequestParam("student-id") int studentId, @RequestParam("course") int course) throws ServiceException {
        studentService.assignCourse(studentId, course);
    }

    @PutMapping("/remove/course")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Remove student from course")
    public void removeFromCourse(@RequestParam("student-id") int studentId) throws ServiceException {
        studentService.removeFromCourse(studentId);
    }

    @PutMapping("/change/course")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change course for student")
    public void changeCourse(@RequestParam("student-id") int studentId, @RequestParam("course") int course) throws ServiceException {
        studentService.changeCourse(studentId, course);
    }
}
