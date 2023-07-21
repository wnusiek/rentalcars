package com.example.rentalcars;

import com.example.rentalcars.model.*;
import com.example.rentalcars.repository.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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


        var car1 = carRepository.save(new CarModel(1l, "Opel", "Corsa", BigDecimal.valueOf(1111), BigDecimal.valueOf(500), "Hatchback", "automatyczna", 5, 4, "benzyna", "aaa", true, new Date(1999),195000,"Red"));
        var car2 = carRepository.save(new CarModel(2l, "Opel", "Astra", BigDecimal.valueOf(456), BigDecimal.valueOf(500), "Sedan", "automatyczna", 5, 4, "olej", "aaa", true, new Date(2019), 45000, "Black"));

        var employee1 = employeeRepository.save(new EmployeeModel(1l, "jan", "kowalski", "saaaa"));
        var employee2 = employeeRepository.save(new EmployeeModel(2l, "jan", "nowak", "saaaa"));
        departmentRepository.save((new DepartmentModel(1l, "lublin", Set.of(employee1), List.of(car1))));
        departmentRepository.save((new DepartmentModel(2l, "kraków", Set.of(employee2), List.of(car2))));

        returnRepository.save(new ReturnModel(1l, 2l, new Date(2023, 07, 16),3l, "bez uszkodzeń"));
        rentalRepository.save(new RentalModel(1l, 1l, new Date(2023, 07, 16),1l, "blablabla"));
        reservationRepository.save(new ReservationModel(1l, 1l, new Date(2023, 07, 20), new Date(2023, 07, 17), BigDecimal.valueOf(100), "Kraków", 2L));
        carRentalRepository.save(new CarRentalModel(1l, "SDACarRental", "SDACarRentalDomain", "SDACarRentalAddress", "SDACarRentalOwner", "SDACarRentalLogotype"));

    }
}
