package com.gmail.sergick6690.restControllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.CathedraForm;
import com.gmail.sergick6690.service.CathedraService;
import com.gmail.sergick6690.universityModels.Cathedra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/cathedras", produces = {"application/xml", "application/json"})
public class CathedraRestController {
    private CathedraService cathedraService;

    @Autowired
    public CathedraRestController(CathedraService cathedraService) {
        this.cathedraService = cathedraService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Cathedra add(@RequestBody CathedraForm cathedraForm) throws ServiceException {
        Cathedra cathedra = cathedraService.createNewCathedra(cathedraForm);
        cathedraService.add(cathedra);
        return cathedra;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Cathedra> showAllCathedras() throws ServiceException {
        return cathedraService.findAll();
    }

    @GetMapping("/{cathedra-id}")
    @ResponseStatus(HttpStatus.OK)
    public Cathedra showById(@PathVariable("cathedra-id") int cathedraId) throws ServiceException {
        return cathedraService.findById(cathedraId);
    }

    @DeleteMapping("/{cathedra-id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCatrhedra(@PathVariable("cathedra-id") int cathedraId) throws ServiceException {
        cathedraService.removeById(cathedraId);

    }
}

