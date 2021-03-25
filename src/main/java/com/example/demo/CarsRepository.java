package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

interface CarsRepository extends JpaRepository<Car, Long> {

}