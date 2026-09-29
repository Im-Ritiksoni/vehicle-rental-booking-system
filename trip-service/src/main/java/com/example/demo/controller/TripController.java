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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.TripServiceApplication;
import com.example.demo.client.DriverClient;
import com.example.demo.client.VehicleClient;
import com.example.demo.dao.TripRepository;
import com.example.demo.dto.DriverDto;
import com.example.demo.dto.VehicleDto;
import com.example.demo.model.Trip;

@RequestMapping("/trips")
@RestController
public class TripController {

    private final TripServiceApplication tripServiceApplication;

	private final TripRepository tripRepository;
	private final DriverClient driverClient;
	private final VehicleClient vehicleClient;
	
	public TripController(TripRepository tripRepository, DriverClient driverClient, VehicleClient vehicleClient, TripServiceApplication tripServiceApplication) {
		
		this.tripRepository = tripRepository;
		this.driverClient = driverClient;
		this.vehicleClient = vehicleClient;
		this.tripServiceApplication = tripServiceApplication;
	}
	
	@PostMapping
	public ResponseEntity<?> createTrip(@RequestParam("driverId")int driverId,
			@RequestParam("vehicleId")int vehicleId,
			@RequestBody Trip trip)
	{
		
		DriverDto driverDto=driverClient.getDriverById(driverId);
		if(driverDto==null)
		{

 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("driver not found");
		}
		
		VehicleDto vehicleDto=vehicleClient.getVehicleById(vehicleId);
		if(vehicleDto==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("vehicle not found");
		}
		
		Trip t=new Trip();
		t.setDriverId(driverId);
		t.setVehicleId(vehicleId);
		
		t.setDueDate(trip.getDueDate());
		t.setRouteDetails(trip.getRouteDetails());
		t.setTitle(trip.getTitle());
		
		tripRepository.save(t);
		return new ResponseEntity<>(t,HttpStatus.CREATED);
	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Trip> getTripById(@PathVariable("id")int id)
	{
		Trip t=tripRepository.findById(id).orElse(null);
		if(t==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(t);
	}
	
	@GetMapping
	public ResponseEntity<List<Trip>> getAllTrips()
	{
		List<Trip> trip=tripRepository.findAll();
		return ResponseEntity.ok(trip);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Trip> deleteTrip(@PathVariable("id")int id)
	{
		if(!tripRepository.existsById(id))
       {
	return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
      
       tripRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Trip> updateTrip(@PathVariable("id")int id,@RequestBody Trip trip)
	{
		Trip t=tripRepository.findById(id).orElse(null);
		if(t==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		t.setDueDate(trip.getDueDate());
		t.setRouteDetails(trip.getRouteDetails());
		t.setTitle(trip.getTitle());
		
		
		tripRepository.save(t);
		return ResponseEntity.ok(t);
	}
	
	
}
