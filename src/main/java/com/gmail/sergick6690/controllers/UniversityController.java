package com.gmail.sergick6690.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UniversityController {
    @GetMapping()
    public String startPage() {
        return "university";
    }
}
