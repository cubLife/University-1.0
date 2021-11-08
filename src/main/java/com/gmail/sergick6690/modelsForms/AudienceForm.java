package com.gmail.sergick6690.modelsForms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AudienceForm {
    @NotNull
    @Min(value = 0, message = "Audience number can't be less than 0")
    private int number;
}
