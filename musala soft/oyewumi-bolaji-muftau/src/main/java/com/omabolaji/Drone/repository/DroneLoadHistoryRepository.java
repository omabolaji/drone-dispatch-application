package com.omabolaji.Drone.repository;

import com.omabolaji.Drone.model.Drone;
import com.omabolaji.Drone.model.DroneLoadHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneLoadHistoryRepository extends JpaRepository<DroneLoadHistory, Long> {
    List<DroneLoadHistory> findAllByDroneOrderByCreatedAtDesc(Drone drone);
}
