package com.omabolaji.Drone.service;

import com.omabolaji.Drone.dto.ApiResponse;
import com.omabolaji.Drone.dto.DroneDto;
import com.omabolaji.Drone.dto.MedicationDto;
import com.omabolaji.Drone.dto.MedicationItemDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DroneService {

    ApiResponse<?> fetchAllDrone();
    ApiResponse<?> fetchAllMedicationItems();
    ApiResponse<?> fetchAllDroneBatteryAudit();
    ApiResponse<?> registeringDrone(DroneDto request);
    ApiResponse<?> createMedicationItem(MedicationDto request);
    ApiResponse<?> loadingDroneWithMedicationItems(long droneId, List<MedicationItemDto> items);
    ApiResponse<?> checkingLoadedMedicationItemsForGivenDrone(long droneId);
    ApiResponse<?> checkingAvailableDronesForLoading();
    ApiResponse<?>  checkDroneBatteryLevelForGivenDrone(long droneId);
}
