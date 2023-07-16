package com.example.rentalcars.repository;

import com.example.rentalcars.model.RentalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<RentalModel, Long> {
}
