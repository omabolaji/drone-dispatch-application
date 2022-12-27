package com.omabolaji.Drone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data @AllArgsConstructor @NoArgsConstructor
public class DroneDto {
    @NotNull
    private String serialNumber;
    @NotNull
    private ModelType model;
    @Min(value = 1, message = "Minimum weight can not be less than 1kg")
    @Max(value = 500, message = "Maximum weight can not exceeded 500kg") @NotNull
    private BigDecimal weightLimit;
    private double batteryCapacity;
    @NotNull
    private DroneState state;
}
