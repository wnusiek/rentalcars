package com.example.rentalcars.repository;

import com.example.rentalcars.DTO.EmployeeDto;
import com.example.rentalcars.model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeModel, Long> {}