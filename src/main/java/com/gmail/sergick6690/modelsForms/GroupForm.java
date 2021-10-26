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
public class GroupForm {
    @NotBlank(message = "Name can't be empty")
    @Size(min = 5, max = 5, message = "Name of group should be equal 5 characters")
    private String name;
    @NotNull
    @Min(value = 1, message = "Value should be more than 0")
    private int scheduleId;
    @NotNull
    @Min(value = 1, message = "Value should be more than 0")
    private int cathedraId;
}
