package com.omabolaji.Drone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "drone_load_history")
@AllArgsConstructor @NoArgsConstructor
public class DroneLoadHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    private Drone drone;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Medication> medications;
    private Date createdAt= new Date();
    private Date modifiedAt = new Date();
}
