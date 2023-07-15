package com.example.rentalcars.repository;

import com.example.rentalcars.model.AvailabilityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<AvailabilityModel, Long> {
}
