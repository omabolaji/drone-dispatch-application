package com.omabolaji.Drone;

import com.omabolaji.Drone.dto.*;
import com.omabolaji.Drone.service.DroneService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class DronesApplication {

	public static void main(String[] args) {
		SpringApplication.run(DronesApplication.class, args);
	}

	@Bean
	CommandLineRunner run(DroneService dispatchService){
		return args -> {
			//todo: register drone service
			dispatchService.registeringDrone(new DroneDto("Drone001", ModelType.Heavyweight,new BigDecimal(450),90, DroneState.LOADING));
			dispatchService.registeringDrone(new DroneDto("Drone002", ModelType.Heavyweight,new BigDecimal(450),80, DroneState.LOADING));
			dispatchService.registeringDrone(new DroneDto("Drone003", ModelType.Cruiserweight,new BigDecimal(300),70, DroneState.LOADING));
			dispatchService.registeringDrone(new DroneDto("Drone004", ModelType.Cruiserweight,new BigDecimal(400),50, DroneState.LOADED));
			dispatchService.registeringDrone(new DroneDto("Drone005", ModelType.Lightweight,new BigDecimal(200),40, DroneState.LOADING));
			dispatchService.registeringDrone(new DroneDto("Drone006", ModelType.Lightweight,new BigDecimal(100),30, DroneState.LOADING));
			dispatchService.registeringDrone(new DroneDto("Drone007", ModelType.Middleweight,new BigDecimal(250),44, DroneState.LOADING));
			dispatchService.registeringDrone(new DroneDto("Drone008", ModelType.Middleweight,new BigDecimal(450),90, DroneState.LOADED));
			dispatchService.registeringDrone(new DroneDto("Drone009", ModelType.Heavyweight,new BigDecimal(300),20, DroneState.DELIVERED));
			dispatchService.registeringDrone(new DroneDto("Drone0010", ModelType.Heavyweight,new BigDecimal(300),25, DroneState.DELIVERING));
			dispatchService.registeringDrone(new DroneDto("Drone0011", ModelType.Heavyweight,new BigDecimal(10),100, DroneState.IDLE));
			dispatchService.registeringDrone(new DroneDto("Drone0012", ModelType.Heavyweight,new BigDecimal(300),60, DroneState.RETURNING));

			//todo: add medication
			dispatchService.createMedicationItem(new MedicationDto("Paracetamol",new BigDecimal(100),"ABC","paracetamol.jpeg"));
			dispatchService.createMedicationItem(new MedicationDto("Panadol",new BigDecimal(50),"ABCDC","panadol.jpeg"));
			dispatchService.createMedicationItem(new MedicationDto("Flagyl",new BigDecimal(400),"POI","flagyl.jpeg"));
			dispatchService.createMedicationItem(new MedicationDto("Pain",new BigDecimal(560),"DSA","painkiller.jpeg"));
			dispatchService.createMedicationItem(new MedicationDto("Aspirin",new BigDecimal(80),"FGDS","aspirin.jpeg"));
			dispatchService.createMedicationItem(new MedicationDto("Doxycap",new BigDecimal(120),"KJHG","doxy.jpeg"));
			dispatchService.createMedicationItem(new MedicationDto("AntiSpot",new BigDecimal(180),"KJHGFS","cream.jpeg"));
			dispatchService.createMedicationItem(new MedicationDto("Tonic",new BigDecimal(40),"OIUYTRE","tonic.jpeg"));
			dispatchService.createMedicationItem(new MedicationDto("VitaminA",new BigDecimal(45),"LKJHG","vitaminA.jpeg"));
			dispatchService.createMedicationItem(new MedicationDto("VitaminB",new BigDecimal(200),"OKJHTFD","vitaminB.jpeg"));

			//todo: loading medication items to drone
			List<MedicationItemDto> medication1 = new ArrayList<>();
			MedicationItemDto itemDto = new MedicationItemDto(13l,new BigDecimal(100));
			MedicationItemDto itemDto2 = new MedicationItemDto(14l,new BigDecimal(50));
			medication1.add(itemDto);
			medication1.add(itemDto2);
			dispatchService.loadingDroneWithMedicationItems(1l,medication1 );

			List<MedicationItemDto> medication2= new ArrayList<>();
			MedicationItemDto itemDto3 = new MedicationItemDto(17l,new BigDecimal(80));
			MedicationItemDto itemDto4= new MedicationItemDto(18l,new BigDecimal(120));
			medication2.add(itemDto3);
			medication2.add(itemDto4);
			dispatchService.loadingDroneWithMedicationItems(5l,medication2 );

		};
	}

}
