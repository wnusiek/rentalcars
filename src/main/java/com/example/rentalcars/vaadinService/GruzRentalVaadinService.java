package com.example.rentalcars.vaadinService;

import com.example.rentalcars.DTO.CarDto;
import com.example.rentalcars.MenagementServices.CarsBySpecificationService;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.repository.*;
import com.example.rentalcars.service.CarService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GruzRentalVaadinService {

    private final CarRepository carRepository;

    private final CarsBySpecificationService carsBySpecificationService;
    private final CarService carService;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final RentalRepository rentalRepository;
    private final ReservationRepository reservationRepository;
    private final ReturnRepository returnRepository;

    public GruzRentalVaadinService(CarRepository carRepository, CarsBySpecificationService carsBySpecificationService, CarService carService, CompanyRepository companyRepository,
                                   CustomerRepository customerRepository, DepartmentRepository departmentRepository,
                                   EmployeeRepository employeeRepository, RentalRepository rentalRepository,
                                   ReservationRepository reservationRepository, ReturnRepository returnRepository) {

        this.carRepository = carRepository;
        this.carsBySpecificationService = carsBySpecificationService;
        this.carService = carService;
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.rentalRepository = rentalRepository;
        this.reservationRepository = reservationRepository;
        this.returnRepository = returnRepository;
    }

    public List<CarDto> findCarsByMark (String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return carService.getCarList();
        } else {
            return carsBySpecificationService.getCarsByMark(filterText);
        }
    }

}
