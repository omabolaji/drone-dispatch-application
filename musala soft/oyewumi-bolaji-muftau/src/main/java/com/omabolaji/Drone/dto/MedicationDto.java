package com.omabolaji.Drone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data @AllArgsConstructor @NoArgsConstructor
public class MedicationDto {
    @NotNull
    private String name;
    @NotNull
    private BigDecimal weight;
    @Pattern(regexp = "[A-Z0-9_]+")
    @NotNull
    private String code;
    private String image;
}
