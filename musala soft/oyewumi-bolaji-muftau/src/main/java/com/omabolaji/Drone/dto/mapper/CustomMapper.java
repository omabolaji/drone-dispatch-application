package com.omabolaji.Drone.dto.mapper;

import com.omabolaji.Drone.dto.DroneDto;
import com.omabolaji.Drone.dto.MedicationDto;
import com.omabolaji.Drone.model.Drone;
import com.omabolaji.Drone.model.DroneLoadHistory;
import com.omabolaji.Drone.model.Medication;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class CustomMapper {

    public abstract Drone createDrone(DroneDto droneDto);
    public abstract Medication createMedication(MedicationDto medicationDto);
    public abstract DroneLoadHistory createDroneLoadHistory(Drone drone, List<Medication> medications);
}
