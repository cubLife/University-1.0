package com.gmail.sergick6690.modelsForms;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ItemForm {
    @NotNull
    @Min(value = 1, message = "Value should be more than 0")
    private int subjectId;
    @NotBlank(message = "Day can't be empty")
    private String day;
    @NotNull
    @Min(value = 9, message = "Hour can't be less than 9")
    @Max(value = 17, message = "Hour can't be bigger than 17")
    private int hour;
    @NotNull
    @Min(value = 1, message = "Value should be more than 0")
    private int audienceId;
    @Min(1)
    @Max(2)
    private int duration;
    @NotNull
    @Min(value = 1, message = "Value should be more than 0")
    private int scheduleId;
}
