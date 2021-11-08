package com.gmail.sergick6690.restControllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.FacultyForm;
import com.gmail.sergick6690.service.FacultyService;
import com.gmail.sergick6690.universityModels.Faculty;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/faculties", produces = {"application/json", "application/xml"})
public class FacultyRestController {
    private FacultyService facultyService;

    @Autowired
    public FacultyRestController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new faculty")
    public Faculty add(@RequestBody FacultyForm facultyForm) throws ServiceException {
        Faculty faculty = facultyService.createNewFaulty(facultyForm);
        facultyService.add(faculty);
        return faculty;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all faculties")
    public List<Faculty> showAllFaculties() throws ServiceException {
        return facultyService.findAll();
    }

    @GetMapping("/{faculty-id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get faulty by id")
    public Faculty showById(@PathVariable("faculty-id") int facultyId) throws ServiceException {
        return facultyService.findById(facultyId);
    }

    @DeleteMapping("/{faculty-id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete faulty by id")
    public void deleteFaculty(@PathVariable("faculty-id") int facultyId) throws ServiceException {
        facultyService.removeById(facultyId);
    }
}