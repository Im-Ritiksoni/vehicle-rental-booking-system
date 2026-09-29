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
import com.example.demo.dao.PaymentRepository;
import com.example.demo.dto.BookingDto;
import com.example.demo.dto.CustomerDto;
import com.example.demo.model.Payment;


@RequestMapping("/payments")
@RestController
public class PaymentController {
	
	private final PaymentRepository paymentRepository;
	private final CustomerClient customerClient;
	private final BookingClient bookingClient;
	
	public PaymentController(PaymentRepository paymentRepository, CustomerClient customerClient,
			BookingClient bookingClient) {
		
		this.paymentRepository = paymentRepository;
		this.customerClient = customerClient;
		this.bookingClient = bookingClient;
	}
	
	@PostMapping
	public ResponseEntity<?> createPayment(@RequestParam("bookingId")int bookingId,
			@RequestParam("customerId")int customerId,
			@RequestBody Payment payment)
	{
		CustomerDto customerDto=customerClient.getCustomerById(customerId);
		if(customerDto==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("customer not found");
		}
		BookingDto bookingDto=bookingClient.getBookingById(bookingId);
		if(bookingDto==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("booking not found");
		}
		
		Payment pay=new Payment();
		
		pay.setCustomerId(customerId);
		pay.setBookingId(bookingId);
		pay.setPaymentDate(LocalDate.now());
		
		pay.setAmount(payment.getAmount());
		pay.setStatus(payment.getStatus());
		
		paymentRepository.save(pay);
		return new ResponseEntity<>(pay,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Payment> getPaymentById(@PathVariable("id")int id)
	{
		Payment pay=paymentRepository.findById(id).orElse(null);
		if(pay==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(pay);
	}
	
	@GetMapping
	public ResponseEntity<List<Payment>> getAllPayment()
	{
		List<Payment> pay= paymentRepository.findAll();
			return ResponseEntity.ok(pay);

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Payment> deletePayment(@PathVariable("id")int id)
	{
		if(!paymentRepository.existsById(id))
		{
			
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		paymentRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	} 
	
	@PutMapping("/{id}")
	public ResponseEntity<Payment> updatePayment(@PathVariable("id")int id,@RequestBody Payment payment)
	{
		Payment pay=paymentRepository.findById(id).orElse(null);
		if(pay==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		pay.setAmount(payment.getAmount());
		pay.setPaymentDate(payment.getPaymentDate());
		pay.setStatus(payment.getStatus());
		
		paymentRepository.save(pay);
		return ResponseEntity.ok(pay);
	}
}
