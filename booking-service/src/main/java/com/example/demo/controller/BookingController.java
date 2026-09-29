package com.example.demo.controller;

import java.time.LocalDate;
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
import com.example.demo.BookingServiceApplication;
import com.example.demo.client.CustomerClient;
import com.example.demo.client.VehicleClient;
import com.example.demo.dao.BookingRepository;
import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.VehicleDto;
import com.example.demo.model.Booking;

@RequestMapping("/bookings")
@RestController
public class BookingController {

    private final BookingServiceApplication bookingServiceApplication;
	
	private final CustomerClient customerClient;
	private final VehicleClient vehicleClient;
	private final BookingRepository bookingRepository;
	
	
	
	
	public BookingController(CustomerClient customerClient, VehicleClient vehicleClient,
			BookingRepository bookingRepository, BookingServiceApplication bookingServiceApplication) {
		this.customerClient = customerClient;
		this.vehicleClient = vehicleClient;
		this.bookingRepository = bookingRepository;
		this.bookingServiceApplication = bookingServiceApplication;
	}




	@PostMapping
	public ResponseEntity<?> createBooking(@RequestParam("customerId")int customerId,
			@RequestParam("vehicleId")int vehicleId,@RequestBody Booking booking)
	{
		
		CustomerDto customerDto=customerClient.getCustomerById(customerId);
		if(customerDto==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("customer not found");
		}
		
		VehicleDto vehicleDto=vehicleClient.getVehicleById(vehicleId);
		
		if(vehicleDto==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("vehicle not found");
		}
		
		Booking book=new Booking();
		
		book.setCustomerId(customerId);
		book.setVehicleId(vehicleId);
		book.setBookingDate(LocalDate.now());
		book.setStatus(booking.getStatus());
		book.setDurationDays(booking.getDurationDays());
		
		bookingRepository.save(book);
		
		return new ResponseEntity<>(book,HttpStatus.CREATED);
		
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Booking> getBookingById(@PathVariable("id")int id)
	{
		Booking book=bookingRepository.findById(id).orElse(null);
		if(book==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(book);
	}
	
	@GetMapping
	public ResponseEntity<List<Booking>> getAllBooking()
	{
		List<Booking> bo=bookingRepository.findAll();
		return ResponseEntity.ok(bo);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> deleteBooking(@PathVariable("id")int id)
	{
		if(!bookingRepository.existsById(id))
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		bookingRepository.deleteById(id);
		
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Booking> updateBooking(@PathVariable("id")int id ,@RequestBody Booking booking)
	{
		Booking book=bookingRepository.findById(id).orElse(null);
		if(book==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		book.setBookingDate(booking.getBookingDate());
		book.setDurationDays(booking.getDurationDays());
		book.setStatus(booking.getStatus());
	
		bookingRepository.save(book);
		return ResponseEntity.ok(book);
	}
	

}
