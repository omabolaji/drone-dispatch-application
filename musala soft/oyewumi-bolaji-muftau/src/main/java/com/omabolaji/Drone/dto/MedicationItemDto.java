package com.omabolaji.Drone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data @AllArgsConstructor @NoArgsConstructor
public class MedicationItemDto {
    @NotNull
    private Long medicationId;
    @NotNull
    private BigDecimal weight;
}
