package com.omabolaji.Drone.model;

import com.omabolaji.Drone.dto.DroneState;
import com.omabolaji.Drone.dto.ModelType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity @Table(name = "drone") @AllArgsConstructor @NoArgsConstructor
public class Drone implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(length = 100,name = "serial_number", unique = true)
    private String serialNumber;
    @Column(nullable = false)
    private ModelType model;
    @Column(nullable = false, name = "weight_limit") @Max(value = 500)
    private BigDecimal weightLimit;
    @Column(nullable = false, name = "battery_capacity")
    private double batteryCapacity;
    @Column(nullable = false)
    private DroneState state;
    private Date createdAt= new Date();
    private Date modifiedAt = new Date();
}
