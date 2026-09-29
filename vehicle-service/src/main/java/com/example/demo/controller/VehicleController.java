package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.VehicleServiceApplication;
import com.example.demo.dao.VehicleRepository;
import com.example.demo.model.Vehicle;

@RequestMapping("/vehicles")
@RestController
public class VehicleController {

    private final VehicleServiceApplication vehicleServiceApplication;

    private final VehicleRepository vehicleRepository;

    VehicleController(VehicleRepository vehicleRepository, VehicleServiceApplication vehicleServiceApplication) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleServiceApplication = vehicleServiceApplication;
    }
	
	@PostMapping
	public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle)
	{
		vehicleRepository.save(vehicle);
		return new ResponseEntity<>(vehicle,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Vehicle> getVehicleById(@PathVariable("id")int id)
	{
		Vehicle veh=vehicleRepository.findById(id).orElse(null);
		if(veh==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(veh);
	}
	
	@GetMapping
	public ResponseEntity<List<Vehicle>> getAllVehicle()
	{
		List<Vehicle> veh =vehicleRepository.findAll();
		return ResponseEntity.ok(veh);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Vehicle> deleteVehicle(@PathVariable("id")int id)
	{
		if(!vehicleRepository.existsById(id))
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		vehicleRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Vehicle> updateVehicle(@PathVariable("id")int id,@RequestBody Vehicle vehicle)
	{
		Vehicle veh=vehicleRepository.findById(id).orElse(null);
		if(veh==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		veh.setVehicleName(vehicle.getVehicleName());
		veh.setModel(vehicle.getModel());
		veh.setType(vehicle.getType());
		veh.setDailyFee(vehicle.getDailyFee());
		vehicleRepository.save(veh);
		return ResponseEntity.ok(veh);
	}

}
