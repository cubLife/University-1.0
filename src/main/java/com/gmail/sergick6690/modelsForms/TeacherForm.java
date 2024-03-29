package com.gmail.sergick6690.modelsForms;

import com.gmail.sergick6690.universityModels.Human;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TeacherForm extends Human {
    @NotBlank(message = "Degrre can't be empty")
    @Size(max = 30, message = "Degree should be less or equal 30 characters")
    private String degree;
    @NotNull
    @Min(value = 1, message = "Schedule id can't be less than 1")
    private int scheduleId;

    public TeacherForm(String firstName, String lastName, String sex, int age, @NotBlank(message = "Degrre can't be empty") @Size(max = 30, message = "Degree should be less or equal 30 characters") String degree, @NotNull @Min(value = 1, message = "Schedule id can't be less than 1") int scheduleId) {
        super(firstName, lastName, sex, age);
        this.degree = degree;
        this.scheduleId = scheduleId;
    }
}
