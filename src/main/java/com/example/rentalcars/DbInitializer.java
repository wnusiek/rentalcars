package com.example.rentalcars;

import com.example.rentalcars.model.*;
import com.example.rentalcars.repository.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Component
public class DbInitializer implements ApplicationRunner {
    private final CarRepository carRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ReturnRepository returnRepository;
    private final RentalRepository rentalRepository;
    private final ReservationRepository reservationRepository;

    private final CarRentalRepository carRentalRepository;

    public DbInitializer(CarRepository carRepository, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, ReturnRepository returnRepository, RentalRepository rentalRepository, ReservationRepository reservationRepository, CarRentalRepository carRentalRepository) {
        this.carRepository = carRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.returnRepository = returnRepository;
        this.rentalRepository = rentalRepository;
        this.reservationRepository = reservationRepository;
        this.carRentalRepository = carRentalRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        carRepository.save(new CarModel(1l, "Opel", "Corsa", BigDecimal.ONE, BigDecimal.valueOf(500), "Sedan", "automatyczna", 5, 4, "benzyna", "aaa", true));
        var employee1 = employeeRepository.save(new EmployeeModel(1l, "jan", "kowalski", "saaaa"));
        departmentRepository.save((new DepartmentModel(1l, "lublin", Set.of(employee1))));
        returnRepository.save(new ReturnModel(1l, 2l, new Date(2023, 07, 16),3l, "bez uszkodzeń"));
        rentalRepository.save(new RentalModel(1l));
        reservationRepository.save(new ReservationModel(1l, 1l, new Date(2023, 07, 20), new Date(2023, 07, 17), BigDecimal.valueOf(100), "Kraków"));
        carRentalRepository.save(new CarRentalModel(1l, "SDACarRental", "SDACarRentalDomain", "SDACarRentalAddress", "SDACarRentalOwner", "SDACarRentalLogotype"));

    }
}
