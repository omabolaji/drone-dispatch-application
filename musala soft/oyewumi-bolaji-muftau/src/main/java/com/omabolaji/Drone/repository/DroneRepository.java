package com.omabolaji.Drone.repository;

import com.omabolaji.Drone.dto.DroneState;
import com.omabolaji.Drone.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {
    List<Drone> findAllByStateOrderByCreatedAtDesc(DroneState state);
    List<Drone> findAllByStateIsNotNull();
    List<Drone> findAllByBatteryCapacityIsLessThan(double batteryLevel);
}
