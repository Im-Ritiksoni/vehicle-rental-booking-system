package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.CustomerDto;

@FeignClient(name="customer-service", dismiss404 = true)
public interface CustomerClient {
	
	@GetMapping("/customers/{id}")
	public CustomerDto getCustomerById(@PathVariable("id")int id);

}
