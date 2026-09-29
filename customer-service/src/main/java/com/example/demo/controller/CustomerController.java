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

import com.example.demo.dao.CustomerRepository;
import com.example.demo.model.Customer;



@RequestMapping("/customers")
@RestController
public class CustomerController {
	
	private final CustomerRepository customerRepository;
	
	
	
	public CustomerController(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}



	@PostMapping
	public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer)
	{
		customerRepository.save(customer);
		return new ResponseEntity<>(customer,HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id")int id)
	{
		Customer cus=customerRepository.findById(id).orElse(null);
		if(cus==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(cus);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Customer> deleteCustomer(@PathVariable("id")int id)
	{
		if(!customerRepository.existsById(id))
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		customerRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).build();
		
		
	}
	
	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomer()
	{
		List<Customer> cust=customerRepository.findAll();
		return ResponseEntity.ok(cust);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable("id")int id,@RequestBody Customer customer)
	{
		Customer cu=customerRepository.findById(id).orElse(null);
		if(cu==null)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		cu.setName(customer.getName());
		cu.setEmail(customer.getEmail());
		cu.setMobile(customer.getMobile());
		
		customerRepository.save(cu);
		return ResponseEntity.ok(cu);
	}
    
	
	

}
