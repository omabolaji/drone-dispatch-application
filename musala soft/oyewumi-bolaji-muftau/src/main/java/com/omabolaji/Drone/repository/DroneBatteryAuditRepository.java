package com.omabolaji.Drone.repository;

import com.omabolaji.Drone.model.DroneBatteryAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneBatteryAuditRepository extends JpaRepository<DroneBatteryAudit, Long> {
}
