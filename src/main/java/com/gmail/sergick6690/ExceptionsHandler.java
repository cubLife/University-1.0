package com.gmail.sergick6690;

import com.gmail.sergick6690.exceptions.ServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(ServiceException.class)
    public ModelAndView serviceExceptionHandler(ServiceException ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("message", ex.getMessage());
        return model;
    }
}
