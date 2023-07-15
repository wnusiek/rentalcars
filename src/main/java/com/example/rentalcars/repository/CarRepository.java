package com.example.rentalcars.repository;

import com.example.rentalcars.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<CarModel, Long> {
}
