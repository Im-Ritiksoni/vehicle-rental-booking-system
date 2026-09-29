package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.TripDto;

@FeignClient(name="trip-service", dismiss404 = true)
public interface TripClient {
	
	@GetMapping("/trips/{id}")
	public TripDto getTripById(@PathVariable("id")int id);

}
