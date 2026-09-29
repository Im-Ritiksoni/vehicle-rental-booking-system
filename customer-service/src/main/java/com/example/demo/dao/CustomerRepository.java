package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
