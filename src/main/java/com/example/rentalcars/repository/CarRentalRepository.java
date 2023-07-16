package com.example.rentalcars.repository;

import com.example.rentalcars.model.CarRentalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRentalRepository extends JpaRepository<CarRentalModel, Long> {
}
