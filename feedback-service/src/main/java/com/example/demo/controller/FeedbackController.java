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

import com.example.demo.client.BookingClient;
import com.example.demo.client.CustomerClient;
import com.example.demo.client.TripClient;
import com.example.demo.dao.FeedbackRepository;
import com.example.demo.dto.BookingDto;
import com.example.demo.dto.CustomerDto;
import com.example.demo.dto.TripDto;
import com.example.demo.model.Feedback;

@RequestMapping("/feedbacks")
@RestController
public class FeedbackController {

	private final FeedbackRepository feedbackRepository;
	private final BookingClient bookingClient;
	private final TripClient tripClient;
	private final CustomerClient customerClient;
	
	public FeedbackController(FeedbackRepository feedbackRepository, BookingClient bookingClient, TripClient tripClient,
			CustomerClient customerClient) {
	
		this.feedbackRepository = feedbackRepository;
		this.bookingClient = bookingClient;
		this.tripClient = tripClient;
		this.customerClient = customerClient;
	}
	
	@PostMapping
	public ResponseEntity<?> createFeedback(@RequestParam("customerId")int customerId,
			@RequestParam("tripId")int tripId,
			@RequestParam("bookingId")int bookingId,@RequestBody Feedback feedback)
	{
		
		CustomerDto customerDto=customerClient.getCustomerById(customerId);
		if(customerDto==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		BookingDto bookingDto=bookingClient.getBookingById(bookingId);
		if(bookingDto==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		TripDto tripDto=tripClient.getTripById(tripId);
		if(tripDto==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		
		Feedback feed=new Feedback();
		feed.setBookingId(bookingId);
		feed.setCustomerId(customerId);
		feed.setTripId(tripId);
		feed.setFeedbackDate(LocalDate.now());
		
		feed.setComments(feedback.getComments());
		feed.setRatingScore(feedback.getRatingScore());
		
		feedbackRepository.save(feed);
		return new ResponseEntity<>(feed,HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Feedback> getFeedbackById(@PathVariable("id")int id)
	{
		Feedback feedback=feedbackRepository.findById(id).orElse(null);
		if(feedback==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(feedback);
	}
	
	@GetMapping
	public ResponseEntity<List<Feedback>> getAllFeedback()
	{
		List<Feedback> f=feedbackRepository.findAll();
		return ResponseEntity.ok(f);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Feedback> deleteFeedback(@PathVariable("id")int id)
	{
		if(!feedbackRepository.existsById(id))
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		feedbackRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Feedback> updateFeedback(@PathVariable("id")int id,@RequestBody Feedback feedback)
	{
		Feedback fe=feedbackRepository.findById(id).orElse(null);
		if(fe==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		fe.setFeedbackDate(feedback.getFeedbackDate());
		fe.setRatingScore(feedback.getRatingScore());
		fe.setComments(feedback.getComments());
		
		feedbackRepository.save(fe);
		return ResponseEntity.ok(fe);
	}
	
	
	
}
