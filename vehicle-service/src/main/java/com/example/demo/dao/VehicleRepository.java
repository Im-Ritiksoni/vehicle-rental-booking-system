package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

}
