package com.gmail.sergick6690.restControllers;

import com.gmail.sergick6690.exceptions.ServiceException;
import com.gmail.sergick6690.service.AudienceService;
import com.gmail.sergick6690.universityModels.Audience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "dev/audiences", produces = {"application/xml", "application/json"})
public class AudienceRestController {
    private AudienceService audienceService;

    @Autowired
    public AudienceRestController(AudienceService audienceService) {
        this.audienceService = audienceService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Audience addAudience(@RequestBody Audience audience) throws ServiceException {
        audienceService.add(audience);
        return audience;
    }

    @GetMapping("/{audienceId}")
    @ResponseStatus(HttpStatus.OK)
    public Audience getAudience(@PathVariable int audienceId) throws ServiceException {
        return audienceService.findById(audienceId);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Audience> showAllAudience() throws ServiceException {
        audienceService.findAll();
        return audienceService.findAll();
    }

    @DeleteMapping("/{audienceId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAudience(@PathVariable int audienceId) throws ServiceException {
        audienceService.removeById(audienceId);
    }
}
