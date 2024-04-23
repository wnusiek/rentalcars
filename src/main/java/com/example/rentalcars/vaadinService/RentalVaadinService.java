package com.example.rentalcars.vaadinService;

import com.example.rentalcars.DTO.CarDto;
import com.example.rentalcars.MenagementServices.CarsByDateService;
import com.example.rentalcars.MenagementServices.CarsBySpecificationService;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.repository.*;
import com.example.rentalcars.service.CarService;
import com.example.rentalcars.service.DepartmentService;
import com.example.rentalcars.service.ReservationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalVaadinService {

    private final CarRepository carRepository;

    private final CarsBySpecificationService carsBySpecificationService;
    private final CarService carService;
    private final DepartmentService departmentService;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final RentalRepository rentalRepository;
    private final ReservationRepository reservationRepository;
    private final ReturnRepository returnRepository;
    private final CarsByDateService carsBydateService;
    private final ReservationService reservationService;

    public RentalVaadinService(CarRepository carRepository, CarsBySpecificationService carsBySpecificationService, CarService carService, DepartmentService departmentService, CompanyRepository companyRepository,
                               CustomerRepository customerRepository, DepartmentRepository departmentRepository,
                               EmployeeRepository employeeRepository, RentalRepository rentalRepository,
                               ReservationRepository reservationRepository, ReturnRepository returnRepository, CarsByDateService carsBydateService, ReservationService reservationService) {

        this.carRepository = carRepository;
        this.carsBySpecificationService = carsBySpecificationService;
        this.carService = carService;
        this.departmentService = departmentService;
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

    public List<CarModel> findAvailableCarsByDates (Boolean availabilityCheckbox, DepartmentModel departmentModel, LocalDate startDate, LocalDate endDate){
        try {
            return reservationService.getAvailableCarsByDateRange(findCarsByDepartment(departmentModel, availabilityCheckbox),startDate, endDate);
        } catch (NullPointerException n) {
            return findCarsByDepartment(departmentModel, availabilityCheckbox);
        }
    }

    public List<CarModel> findCarsByDepartment (DepartmentModel departmentModel, Boolean availabilityCheckbox){
        try {
            if (availabilityCheckbox){
                return carService.getAvailableCars(departmentService.getAllCarsByDepartment(departmentModel.getId()));
            } else return departmentService.getAllCarsByDepartment(departmentModel.getId());

        } catch (NullPointerException n) {
            if (availabilityCheckbox){
                return carService.getAvailableCars(carService.getCarList1());
            } else return carService.getCarList1();
        }
    }



    /*public List<CarModel> findCarsByAvailability (CarStatus status) {
        try{

        }
    }*/
}
