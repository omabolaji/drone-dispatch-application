package com.omabolaji.Drone.service.impl;

import com.omabolaji.Drone.dto.*;
import com.omabolaji.Drone.dto.mapper.CustomMapper;
import com.omabolaji.Drone.model.Drone;
import com.omabolaji.Drone.model.DroneBatteryAudit;
import com.omabolaji.Drone.model.DroneLoadHistory;
import com.omabolaji.Drone.model.Medication;
import com.omabolaji.Drone.repository.DroneBatteryAuditRepository;
import com.omabolaji.Drone.repository.DroneLoadHistoryRepository;
import com.omabolaji.Drone.repository.DroneRepository;
import com.omabolaji.Drone.repository.MedicationRepository;
import com.omabolaji.Drone.service.DroneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service @Slf4j @RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    private final DroneLoadHistoryRepository loadHistoryRepository;
    private final DroneBatteryAuditRepository droneBatteryAuditRepository;
    @Autowired
    private CustomMapper customMapper;


    @Override
    public ApiResponse<?> fetchAllDrone() {
        try {
            List<Drone> drones = droneRepository.findAll(Sort.by(Sort.Direction.DESC,"createdAt"));
            return new ApiResponse<>(true,ApiResponse.Code.SUCCESS,"SUCCESS", drones);
        }catch (Exception ex){
            log.info("An err fetching drones: {}",ex.getLocalizedMessage());
            return new ApiResponse<>(false,ApiResponse.Code.BAD_REQUEST,"Something went wrong",null);
        }
    }

    @Override
    public ApiResponse<?> fetchAllMedicationItems() {
        try {
            List<Medication> medications = medicationRepository.findAll(Sort.by(Sort.Direction.DESC,"createdAt"));
            return new ApiResponse<>(true,ApiResponse.Code.SUCCESS,"SUCCESS", medications);
        }catch (Exception ex){
            log.info("An err fetching medications: {}",ex.getLocalizedMessage());
            return new ApiResponse<>(false,ApiResponse.Code.BAD_REQUEST,"Something went wrong",null);
        }
    }

    @Override
    public ApiResponse<?> fetchAllDroneBatteryAudit() {
        try {
            List<DroneBatteryAudit> droneBatteryAudits = droneBatteryAuditRepository.findAll(Sort.by(Sort.Direction.DESC,"createdAt"));
            return new ApiResponse<>(true,ApiResponse.Code.SUCCESS,"SUCCESS", droneBatteryAudits);
        }catch (Exception ex){
            log.info("An err fetching battery audit: {}",ex.getLocalizedMessage());
            return new ApiResponse<>(false,ApiResponse.Code.BAD_REQUEST,"Something went wrong",null);
        }
    }

    @Override @Transactional
    public ApiResponse<?> registeringDrone(DroneDto request) {
        try {
            if(request.getWeightLimit().compareTo(new BigDecimal(500)) > 0)
                return new ApiResponse<>(true,ApiResponse.Code.BAD_REQUEST,"Maximum weight can not exceeded 500kg", null);

            Drone drone = customMapper.createDrone(request);
            droneRepository.save(drone);
            return new ApiResponse<>(true,ApiResponse.Code.SUCCESS,"Drone created successful", drone);
        }catch (Exception ex){
            log.info("An err create drone: {}",ex.getLocalizedMessage());
            return new ApiResponse<>(false,ApiResponse.Code.BAD_REQUEST,"Something went wrong",null);
        }
    }

    @Override @Transactional
    public ApiResponse<?> createMedicationItem(MedicationDto request) {
        try {
            Medication medication = customMapper.createMedication(request);
            medicationRepository.save(medication);
            return new ApiResponse<>(true,ApiResponse.Code.SUCCESS,"Medication Item added successful", medication);
        }catch (Exception ex){
            log.info("An err create medication: {}",ex.getLocalizedMessage());
            return new ApiResponse<>(false,ApiResponse.Code.BAD_REQUEST,"Something went wrong",null);
        }
    }

    @Override @Transactional
    public ApiResponse<?> loadingDroneWithMedicationItems(long droneId, List<MedicationItemDto> items) {
        try {
            Optional<Drone> drone = droneRepository.findById(droneId);
            if(!drone.isPresent())
                return new ApiResponse<>(false,ApiResponse.Code.NOT_FOUND, "Drone not found", null);

            //todo: check if drone is IDLE OR Loading
            if(!drone.get().getState().equals(DroneState.IDLE)
                    && !drone.get().getState().equals(DroneState.LOADING)
                    && !drone.get().getState().equals(DroneState.RETURNING)){

                return new ApiResponse<>(false,ApiResponse.Code.BAD_REQUEST, "Drone not available for loading", null);
            }

            //todo: check is drone battery is less than 25% then prevent the drone from loading
            boolean isLowBattery = isDroneBatteryLow(drone.get().getBatteryCapacity());
            if(isLowBattery)
                return new ApiResponse<>(false, ApiResponse.Code.BAD_REQUEST, "Drone battery low, can not load any items at the moment", null);

            //todo: compare drone weight capacity with items weight to be loaded on it
            BigDecimal totalWeight = BigDecimal.ZERO;
            List<Medication> medicationList = new ArrayList<>();
            for(MedicationItemDto weight: items){
                Optional<Medication> medication = medicationRepository.findById(weight.getMedicationId());
                if(!medication.isPresent())
                    return new ApiResponse<>(false, ApiResponse.Code.NOT_FOUND, "Medication item with Id: "+weight.getMedicationId()+" not found", null);

                totalWeight = totalWeight.add(weight.getWeight());
                medicationList.add(medication.get());
            }

            if(totalWeight.compareTo(drone.get().getWeightLimit()) > 0){
                return new ApiResponse<>(false,ApiResponse.Code.BAD_REQUEST,"Medication items weight is more than drone weight",null);
            }

            //todo: create drone load log
            DroneLoadHistory loadHistory = customMapper.createDroneLoadHistory(drone.get(),medicationList);
            loadHistory.setDrone(drone.get());
            loadHistoryRepository.save(loadHistory);
            //todo: update drone to loaded
            drone.get().setState(DroneState.LOADED);
            drone.get().setModifiedAt(new Date());
            droneRepository.save(drone.get());

            return new ApiResponse<>(true, ApiResponse.Code.SUCCESS,"Drone loaded successful",loadHistory);
        }catch (Exception ex){
            log.info("An err loading drone: {}",ex.getLocalizedMessage());
            return new ApiResponse<>(false,ApiResponse.Code.BAD_REQUEST,"Something went wrong",null);
        }
    }

    @Override
    public ApiResponse<?> checkingLoadedMedicationItemsForGivenDrone(long droneId) {
        try {
            Optional<Drone> drone = droneRepository.findById(droneId);
            if(!drone.isPresent())
                return new ApiResponse<>(false,ApiResponse.Code.NOT_FOUND, "Drone not found", null);

            if(!drone.get().getState().equals(DroneState.LOADED))
                return new ApiResponse<>(false,ApiResponse.Code.BAD_REQUEST,"The selected drone is yet to be loaded with medication items", null);

            List<DroneLoadHistory> loadedDrone = loadHistoryRepository.findAllByDroneOrderByCreatedAtDesc(drone.get());
            return new ApiResponse<>(true,ApiResponse.Code.SUCCESS,"SUCCESS",loadedDrone);
        }catch (Exception ex){
            log.info("An err checking loaded drone medication item: {}",ex.getLocalizedMessage());
            return new ApiResponse<>(false,ApiResponse.Code.BAD_REQUEST,"Something went wrong",null);
        }
    }

    @Override
    public ApiResponse<?> checkingAvailableDronesForLoading() {
        try {
            List<Drone> fetchAllDrone = droneRepository.findAllByStateIsNotNull();
            if(fetchAllDrone.size() < 1)
                return new ApiResponse<>(false, ApiResponse.Code.NOT_FOUND,"No drone available for loading", null);

            List<Drone> getAvailableDrone = fetchAllDrone.stream().filter(d-> d.getState().equals(DroneState.LOADING)
                    || d.getState().equals(DroneState.IDLE) || d.getState().equals(DroneState.RETURNING)).collect(Collectors.toList());

            log.info("fetchAllDrone: {}",fetchAllDrone.size());
            log.info("getAvailableDrone: {}",getAvailableDrone.size());
            return new ApiResponse<>(true,ApiResponse.Code.SUCCESS,"SUCCESS", getAvailableDrone);
        }catch (Exception ex){
            log.info("An err getting available drones: {}",ex.getLocalizedMessage());
            return new ApiResponse<>(false,ApiResponse.Code.BAD_REQUEST,"Something went wrong",null);
        }
    }

    @Override
    public ApiResponse<?>  checkDroneBatteryLevelForGivenDrone(long droneId) {
        try {
            Optional<Drone> drone = droneRepository.findById(droneId);
            if(!drone.isPresent())
                return new ApiResponse<>(false,ApiResponse.Code.NOT_FOUND, "Drone not found", null);

            //todo: check battery level
            boolean batteryLevel = isDroneBatteryLow(drone.get().getBatteryCapacity());
            if(batteryLevel)
                return new ApiResponse<>(true, ApiResponse.Code.SUCCESS,"Low battery with "+drone.get().getBatteryCapacity()+"%", null);

            return new ApiResponse<>(true, ApiResponse.Code.SUCCESS,"Battery level is "+drone.get().getBatteryCapacity()+"%", null);
        }catch (Exception ex){
            log.info("Err getting loan battery: {}",ex.getLocalizedMessage());
            return new ApiResponse<>(false,ApiResponse.Code.BAD_REQUEST,"Something went wrong",null);
        }
    }

    private boolean isDroneBatteryLow(double battery){
        double lowBattery = 25;
        if(battery < lowBattery)
            return true;

        return false;
    }


        //todo: Introduce a periodic task to check drones battery levels and create history/audit event log for this.
    @Scheduled(cron = "0 0/9 * * * ?") //todo: every 9 minute for testing.
    public void runDroneBatteryAuditTask(){
        try {
            log.info("ABOUT TO LOG DRONE BATTERY AUDIT");
            double lowBattery = 25;
            //todo: fetch all drone with battery level less than 25%
            List<Drone> fetchAllDroneWithLowBatteryLevel = droneRepository.findAllByBatteryCapacityIsLessThan(lowBattery);
            if(fetchAllDroneWithLowBatteryLevel.size() < 1){
                log.info("No drone with low battery level");
                return;
            }
            //todo: run audit if drone exist with low battery
            List<DroneBatteryAudit> audits = new ArrayList<>();
            for(Drone drone: fetchAllDroneWithLowBatteryLevel){
                DroneBatteryAudit batteryAudit = new DroneBatteryAudit();
                batteryAudit.setDrone(drone);
                audits.add(batteryAudit);
            }
            droneBatteryAuditRepository.saveAll(audits);
            log.info(audits.size()+" Drone with low battery level successfully logged");
        }catch (Exception ex){
            log.info("Audit task err: {}",ex.getLocalizedMessage());
        }
    }


}
