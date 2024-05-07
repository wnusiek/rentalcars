package com.example.rentalcars.service;

import com.example.rentalcars.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrmService {
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final ReservationRepository reservationRepository;
    private final RentalRepository rentalRepository;
    private final ReturnRepository returnRepository;

    public long countCars() {
        return carRepository.count();
    }

    public long countEmployees() {
        return employeeRepository.count();
    }

    public long countCustomers() {
        return customerRepository.count();
    }

    public long countReservations() {
        return reservationRepository.count();
    }

    public long countRentals() {
        return rentalRepository.count();
    }

    public long countReturns() {
        return returnRepository.count();
    }

    public long countDepartments() {
        return departmentRepository.count();
    }
}
