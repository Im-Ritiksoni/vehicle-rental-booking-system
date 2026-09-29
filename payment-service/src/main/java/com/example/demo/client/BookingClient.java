package com.example.demo.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.BookingDto;


@FeignClient(name="booking-service", dismiss404 = true)
public interface BookingClient {
	
	@GetMapping("/bookings/{id}")
	public BookingDto getBookingById(@PathVariable("id")int id);

}
