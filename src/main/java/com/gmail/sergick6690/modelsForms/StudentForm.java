package com.gmail.sergick6690.modelsForms;

import com.gmail.sergick6690.universityModels.Human;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

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

    public StudentForm(@NotBlank(message = "First name can't be empty") @Size(min = 3, message = "First name size can't be less than 3 characters") String firstName, @NotBlank(message = "Last name can't be empty") @Size(min = 3, message = "Last name size can't be less than 3 characters") String lastName, @NotBlank(message = "Sex can't be empty") @Size(min = 3, max = 5, message = "Sex size should be between 3 and 5 characters") String sex, @Min(value = 1, message = "Age should be more than 0") int age, @Min(value = 1, message = "Course can't be less than 1") int groupId, @NotNull @Min(value = 1, message = "Course can't be less 1") @Max(value = 7, message = "Course can't be bigger than 7") int course) {
        super(firstName, lastName, sex, age);
        this.groupId = groupId;
        this.course = course;
    }
}
