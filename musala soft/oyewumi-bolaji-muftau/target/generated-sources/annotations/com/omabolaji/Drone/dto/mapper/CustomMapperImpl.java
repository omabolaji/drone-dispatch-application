package com.omabolaji.Drone.dto.mapper;

import com.omabolaji.Drone.dto.DroneDto;
import com.omabolaji.Drone.dto.MedicationDto;
import com.omabolaji.Drone.model.Drone;
import com.omabolaji.Drone.model.DroneLoadHistory;
import com.omabolaji.Drone.model.Medication;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-26T23:51:33+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class CustomMapperImpl extends CustomMapper {

    @Override
    public Drone createDrone(DroneDto droneDto) {
        if ( droneDto == null ) {
            return null;
        }

        Drone drone = new Drone();

        drone.setSerialNumber( droneDto.getSerialNumber() );
        drone.setModel( droneDto.getModel() );
        drone.setWeightLimit( droneDto.getWeightLimit() );
        drone.setBatteryCapacity( droneDto.getBatteryCapacity() );
        drone.setState( droneDto.getState() );

        return drone;
    }

    @Override
    public Medication createMedication(MedicationDto medicationDto) {
        if ( medicationDto == null ) {
            return null;
        }

        Medication medication = new Medication();

        medication.setName( medicationDto.getName() );
        medication.setWeight( medicationDto.getWeight() );
        medication.setCode( medicationDto.getCode() );
        medication.setImage( medicationDto.getImage() );

        return medication;
    }

    @Override
    public DroneLoadHistory createDroneLoadHistory(Drone drone, List<Medication> medications) {
        if ( drone == null && medications == null ) {
            return null;
        }

        DroneLoadHistory droneLoadHistory = new DroneLoadHistory();

        if ( drone != null ) {
            droneLoadHistory.setId( drone.getId() );
            droneLoadHistory.setCreatedAt( drone.getCreatedAt() );
            droneLoadHistory.setModifiedAt( drone.getModifiedAt() );
        }
        if ( medications != null ) {
            List<Medication> list = medications;
            if ( list != null ) {
                droneLoadHistory.setMedications( new ArrayList<Medication>( list ) );
            }
        }

        return droneLoadHistory;
    }
}
