package com.gmail.sergick6690.modelsForms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacultyForm {
    @NotBlank(message = "Name can't be empty")
    @Size(min = 3, message = "Name size can't be less than 3 characters")
    private String name;
}
