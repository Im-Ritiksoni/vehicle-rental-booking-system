package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Driver;

public interface DriverRepository extends JpaRepository<Driver, Integer> {

}
