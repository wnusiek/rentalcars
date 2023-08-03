package com.example.rentalcars;

import com.example.rentalcars.enums.*;
import com.example.rentalcars.model.*;
import com.example.rentalcars.repository.*;
import com.example.rentalcars.service.DepartmentService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DbInitializer implements ApplicationRunner {

    private final CarRepository carRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ReturnRepository returnRepository;
    private final RentalRepository rentalRepository;
    private final ReservationRepository reservationRepository;
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;

    private final DepartmentService departmentService;

    private final UserRepository userRepository;

    public DbInitializer(CarRepository carRepository, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, ReturnRepository returnRepository, RentalRepository rentalRepository, ReservationRepository reservationRepository, CustomerRepository customerRepository, CompanyRepository companyRepository, DepartmentService departmentService, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.returnRepository = returnRepository;
        this.rentalRepository = rentalRepository;
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
        this.departmentService = departmentService;
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var dep1 = departmentRepository.save((new DepartmentModel(1l, "Lublin")));
        var dep2 = departmentRepository.save((new DepartmentModel(2l, "kraków")));
        var dep3 = departmentRepository.save((new DepartmentModel(3l, "Katowice")));
        var dep4 = departmentRepository.save((new DepartmentModel(4l, "Warszawa")));

        var car1 = carRepository.save(new CarModel(1l, "Opel", "Corsa", BigDecimal.valueOf(1111), BigDecimal.valueOf(500), BodyType.HATCHBACK, GearboxType.AUTOMATIC, 5, 4, FuelType.PETROL, "aaa", CarStatus.AVAILABLE, "Red", 128000, 2004));
        var car2 = carRepository.save(new CarModel(2l, "Opel", "Astra", BigDecimal.valueOf(456), BigDecimal.valueOf(500), BodyType.SEDAN, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.AVAILABLE, "Black", 45000, 2019));
        var car3 = carRepository.save(new CarModel(3l, "BMW", "E3śmieć", BigDecimal.valueOf(124), BigDecimal.valueOf(500), BodyType.COUPE, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.AVAILABLE, "Black", 45000, 2019));
        var car4 = carRepository.save(new CarModel(4l, "AUDI", "80", BigDecimal.valueOf(123), BigDecimal.valueOf(500), BodyType.SEDAN, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.AVAILABLE, "Black", 45000, 2019));

        departmentService.addCarToDepartment(4l,1l);
        departmentService.addCarToDepartment(3l,2l);
        departmentService.addCarToDepartment(2l,3l);
        departmentService.addCarToDepartment(1l,1l);

        var employee1 = employeeRepository.save(new EmployeeModel(1l, "jan", "kowalski", EmployeePosition.EMPLOYEE));
        var employee2 = employeeRepository.save(new EmployeeModel(2l, "karol", "nowak", EmployeePosition.MANAGER));

        var customer1 = customerRepository.save(new CustomerModel(1l, "Janusz", "ChceszWMorde", "123456789", "qwerty", "janusz@gmail.com", "99122402212", "Pcim", "32-432"));
        var customer2 = customerRepository.save(new CustomerModel(2l, "Dżesika", "CoSięGapisz", "123456789", "qwerty", "", "", "", ""));

        var user1 = userRepository.save(new UserModel(1L, "houseinik","1234", "dominik@chuj.pl"));

//        var reservation1 = reservationRepository.save(new ReservationModel(1l, car1, LocalDate.of(2023, 7, 20), LocalDate.of(2023, 7, 25), BigDecimal.valueOf(100), dep2, dep2, customer1));
//        var reservation2 = reservationRepository.save(new ReservationModel(2l, car2, LocalDate.of(2023, 8, 20), LocalDate.of(2023, 8, 25), BigDecimal.valueOf(100), dep1, dep3,customer1));
//        var reservation3 = reservationRepository.save(new ReservationModel(3l, car3, LocalDate.of(2023, 6, 20), LocalDate.of(2023, 6, 25), BigDecimal.valueOf(100), dep2, dep4,customer1));
//        var reservation4 = reservationRepository.save(new ReservationModel(4l, car4, LocalDate.of(2023, 9, 20), LocalDate.of(2023, 9, 25), BigDecimal.valueOf(100), dep3, dep3,customer1));

//        rentalRepository.save(new RentalModel(1l, employee1, LocalDate.of(2023, 07, 16), reservation1, "blablabla"));
//        returnRepository.save(new ReturnModel(1l, employee2, LocalDate.of(2023, 07, 16), reservation3, "bez uszkodzeń"));

        companyRepository.save(new CompanyModel(1l, "SDACarRental", "SDACarRentalDomain", "SDACarRentalAddress", "SDACarRentalOwner", "SDACarRentalLogotype"));

    }
}
