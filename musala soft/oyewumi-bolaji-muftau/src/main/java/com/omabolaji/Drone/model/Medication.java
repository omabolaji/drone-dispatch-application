package com.omabolaji.Drone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "medication") @AllArgsConstructor
@NoArgsConstructor
public class Medication implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Pattern(regexp = "[a-zA-Z0-9_-]+") @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal weight;
    @Pattern(regexp = "[A-Z0-9_]+")
    @Column(nullable = false)
    private String code;
    private String image;
    private Date createdAt= new Date();
    private Date modifiedAt = new Date();
}
