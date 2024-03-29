package com.gmail.sergick6690.restControllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.modelsForms.AudienceForm;
import com.gmail.sergick6690.service.AudienceService;
import com.gmail.sergick6690.universityModels.Audience;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/audiences", produces = {"application/xml", "application/json"})
public class AudienceRestController {
    private AudienceService audienceService;

    @Autowired
    public AudienceRestController(AudienceService audienceService) {
        this.audienceService = audienceService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add new audience")
    public Audience addAudience(@RequestBody AudienceForm audienceForm) throws ServiceException {
        Audience audience = new Audience(audienceForm.getNumber());
        audienceService.add(audience);
        return audience;
    }

    @GetMapping("/{audience-id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get audience by id")
    public Audience getAudience(@PathVariable("audience-id") int audienceId) throws ServiceException {
        return audienceService.findById(audienceId);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all All audiences")
    public List<Audience> showAllAudience() throws ServiceException {
        audienceService.findAll();
        return audienceService.findAll();
    }

    @DeleteMapping("/{audience-id}")
    @Operation(summary = "Delete audience by id")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAudience(@PathVariable("audience-id") int audienceId) throws ServiceException {
        audienceService.removeById(audienceId);
    }
}
