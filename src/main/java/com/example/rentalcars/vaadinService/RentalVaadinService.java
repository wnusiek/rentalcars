package com.example.rentalcars.vaadinService;

import com.example.rentalcars.DTO.CarDto;
import com.example.rentalcars.MenagementServices.CarsByDateService;
import com.example.rentalcars.MenagementServices.CarsBySpecificationService;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.repository.*;
import com.example.rentalcars.service.CarService;
import com.example.rentalcars.service.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalVaadinService {

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
    private final CarsByDateService carsBydateService;
    private final ReservationService reservationService;

    public RentalVaadinService(CarRepository carRepository, CarsBySpecificationService carsBySpecificationService, CarService carService, CompanyRepository companyRepository,
                               CustomerRepository customerRepository, DepartmentRepository departmentRepository,
                               EmployeeRepository employeeRepository, RentalRepository rentalRepository,
                               ReservationRepository reservationRepository, ReturnRepository returnRepository, CarsByDateService carsBydateService, ReservationService reservationService) {

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
        this.carsBydateService = carsBydateService;
        this.reservationService = reservationService;
    }

    public List<CarDto> findCarsByMark(String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return carService.getCarList();
        } else {
            return carsBySpecificationService.getCarsByMark(filterText);

        }
    }

    public List<CarModel> findAvailableCarsByDates (LocalDate startDate, LocalDate endDate){
        try {
            return reservationService.getAvailableCarsByDateRange(startDate, endDate);
        } catch (NullPointerException n) {
            return carService.getCarList1();
        }
    }
}