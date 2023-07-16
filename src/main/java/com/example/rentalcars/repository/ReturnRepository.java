package com.example.rentalcars.repository;

import com.example.rentalcars.model.ReturnModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnRepository extends JpaRepository<ReturnModel, Long> {
}
