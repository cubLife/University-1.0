package com.gmail.sergick6690.modelsForms;

import com.gmail.sergick6690.university.Human;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class StudentForm extends Human {
    @Min(value = 1, message = "Course can't be less than 1")
    private int groupId;
    @NotNull
    @Min(value = 1, message = "Course can't be less 1")
    @Max(value = 7, message = "Course can't be bigger than 7")
    private int course;
}
