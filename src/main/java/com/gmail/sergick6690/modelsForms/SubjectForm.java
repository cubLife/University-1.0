package com.gmail.sergick6690.modelsForms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectForm {
    @NotBlank(message = "Name can't be empty")
    @Size(min = 3, message = "Name should be contain no less than 3 characters")
    private String name;
    @NotNull
    @Min(value = 1, message = "Subject id can't be less than 1")
    private int teacherId;
    @NotBlank(message = "Description can't be empty")
    @Size(min = 3, message = "Description should be contain no less than 3 characters")
    private String description;
}
