package com.gmail.sergick6690.restControllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.FacultyService;
import com.gmail.sergick6690.universityModels.Faculty;
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
    public Faculty add(@RequestBody Faculty faculty) throws ServiceException {
        facultyService.add(faculty);
        return faculty;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Faculty> showAllFaculties() throws ServiceException {
        return facultyService.findAll();
    }

    @GetMapping("/{faculty-id}")
    @ResponseStatus(HttpStatus.OK)
    public Faculty showById(@PathVariable("faculty-id") int facultyId) throws ServiceException {
        return facultyService.findById(facultyId);
    }

    @DeleteMapping("/{faculty-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFaculty(@PathVariable("faculty-id") int facultyId) throws ServiceException {
        facultyService.removeById(facultyId);
    }
}