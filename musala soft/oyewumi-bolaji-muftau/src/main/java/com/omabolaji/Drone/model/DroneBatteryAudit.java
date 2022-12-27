package com.omabolaji.Drone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "drone_battery_audit") @AllArgsConstructor
@NoArgsConstructor
public class DroneBatteryAudit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    private Drone drone;
    private Date createdAt= new Date();
    private Date modifiedAt = new Date();
}
