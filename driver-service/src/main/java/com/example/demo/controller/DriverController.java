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
import com.example.demo.dao.DriverRepository;
import com.example.demo.model.Driver;

@RequestMapping("/drivers")
@RestController
public class DriverController {

    private final DriverRepository driverRepository;

    DriverController(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

	@PostMapping
	public ResponseEntity<Driver> addDriver(@RequestBody Driver driver)
	{
	driverRepository.save(driver);
	return new ResponseEntity<>(driver,HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Driver> getDriverById(@PathVariable("id")int id)
	{
		Driver d=driverRepository.findById(id).orElse(null);
		if(d==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(d);
	}
	
	@GetMapping
	public ResponseEntity<List<Driver>> getAllDriver()
	{
		List<Driver> li=driverRepository.findAll();
		return ResponseEntity.ok(li);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Driver> deleteDriver(@PathVariable("id")int id)
	{
		if(!driverRepository.existsById(id))
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		driverRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Driver> updateDriver(@PathVariable("id")int id,@RequestBody Driver driver)
	{
		Driver dr=driverRepository.findById(id).orElse(null);
		if(dr==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		dr.setName(driver.getName());
		dr.setEmail(driver.getEmail());
		dr.setLicenseNumber(driver.getLicenseNumber());
		dr.setSpecialization(driver.getSpecialization());
		
		driverRepository.save(dr);
		return ResponseEntity.ok(dr);
	}
	
	
}
