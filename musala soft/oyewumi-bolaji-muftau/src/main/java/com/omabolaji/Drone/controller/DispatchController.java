package com.omabolaji.Drone.controller;

import com.omabolaji.Drone.dto.ApiResponse;
import com.omabolaji.Drone.dto.DroneDto;
import com.omabolaji.Drone.dto.MedicationDto;
import com.omabolaji.Drone.dto.MedicationItemDto;
import com.omabolaji.Drone.service.DroneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@Slf4j @RequiredArgsConstructor @CrossOrigin
public class DispatchController {

    private final DroneService droneService;

    @GetMapping(path = "/")
    public ResponseEntity<ApiResponse<?>> fetchAllDrone(){
        try {
            ApiResponse<?> response = droneService.fetchAllDrone();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception ex){
            log.info("An err: {}",ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/medications")
    public ResponseEntity<ApiResponse<?>> fetchAllMedicationItems(){
        try {
            ApiResponse<?> response = droneService.fetchAllMedicationItems();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception ex){
            log.info("An err: {}",ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/drone/batteryAudit")
    public ResponseEntity<ApiResponse<?>> fetchAllDroneBatteryAudit(){
        try {
            ApiResponse<?> response = droneService.fetchAllDroneBatteryAudit();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception ex){
            log.info("An err: {}",ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping(path = "/registerDrone")
//    public @ResponseBody ApiResponse<?> registerDrone(@Validated @RequestBody DroneDto request){
//        return droneService.registeringDrone(request);
//    }

    @PostMapping(path = "/registerDrone")
    public ResponseEntity<ApiResponse<?>> registerDrone(@Validated @RequestBody DroneDto request){
        try {
            ApiResponse<?> response = droneService.registeringDrone(request);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception ex){
            log.info("An err: {}",ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/medication/addItem")
    public ResponseEntity<ApiResponse<?>> addMedicationItem(@Validated @RequestBody MedicationDto medicationItem){
        try {
            ApiResponse<?> response = droneService.createMedicationItem(medicationItem);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception ex){
            log.info("An err: {}",ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/drone/{droneId}/loadMedicationItems")
    public ResponseEntity<ApiResponse<?>> loadMedicationItems(
            @PathVariable Long droneId,
            @Validated @RequestBody List<MedicationItemDto> medicationItemList){
        try {
            ApiResponse<?> response = droneService.loadingDroneWithMedicationItems(droneId,medicationItemList);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception ex){
            log.info("An err: {}",ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/drone/{droneId}/checkLoadedMedicationItemsForGivenDrone")
    public ResponseEntity<ApiResponse<?>> checkLoadedMedicationItems(
            @PathVariable Long droneId){
        try {
            ApiResponse<?> response = droneService.checkingLoadedMedicationItemsForGivenDrone(droneId);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception ex){
            log.info("An err: {}",ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(path = "/drone/checkingAvailableDronesForLoading")
    public ResponseEntity<ApiResponse<?>> checkingAvailableDronesForLoading(){
        try {
            ApiResponse<?> response = droneService.checkingAvailableDronesForLoading();
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception ex){
            log.info("An err: {}",ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/drone/{droneId}/checkDroneBatteryLevelForGivenDrone")
    public ResponseEntity<ApiResponse<?>> checkDroneBatteryLevelForGivenDrone(
           @Validated @PathVariable Long droneId){
        try {
            ApiResponse<?> response = droneService.checkDroneBatteryLevelForGivenDrone(droneId);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (Exception ex){
            log.info("An err: {}",ex.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(
            value = "/**",
            method = RequestMethod.OPTIONS
    )
    public ResponseEntity handle() {
        return new ResponseEntity(HttpStatus.OK);
    }

}
