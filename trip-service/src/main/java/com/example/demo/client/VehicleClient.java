package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.VehicleDto;

@FeignClient(name="vehicle-service", dismiss404 = true)
public interface VehicleClient {
	
	@GetMapping("/vehicles/{id}")
	public VehicleDto getVehicleById(@PathVariable("id")int id);

}
