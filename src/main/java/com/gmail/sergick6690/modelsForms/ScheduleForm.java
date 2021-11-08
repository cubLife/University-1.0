package com.gmail.sergick6690.modelsForms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleForm {
    @Size(min = 5, message = "Name should be contain no less than 5 characters")
    private String name;
}
