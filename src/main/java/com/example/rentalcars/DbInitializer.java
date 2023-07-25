package com.example.rentalcars;

import com.example.rentalcars.enums.BodyType;
import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.enums.FuelType;
import com.example.rentalcars.enums.GearboxType;
import com.example.rentalcars.model.*;
import com.example.rentalcars.repository.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

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

    public DbInitializer(CarRepository carRepository, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, ReturnRepository returnRepository, RentalRepository rentalRepository, ReservationRepository reservationRepository, CustomerRepository customerRepository, CompanyRepository companyRepository) {
        this.carRepository = carRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.returnRepository = returnRepository;
        this.rentalRepository = rentalRepository;
        this.reservationRepository = reservationRepository;
        this.customerRepository = customerRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var dep1 = departmentRepository.save((new DepartmentModel(1l, "lublin")));
        var dep2 = departmentRepository.save((new DepartmentModel(2l, "kraków")));

        var car1 = carRepository.save(new CarModel(1l, "Opel", "Corsa", BigDecimal.valueOf(1111), BigDecimal.valueOf(500), BodyType.HATCHBACK, GearboxType.AUTOMATIC, 5, 4, FuelType.PETROL, "aaa", CarStatus.AVAILABLE, "Red", 128000, 2004));
        var car2 = carRepository.save(new CarModel(2l, "Opel", "Astra", BigDecimal.valueOf(456), BigDecimal.valueOf(500), BodyType.SEDAN, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.AVAILABLE, "Black", 45000, 2019));
        var car3 = carRepository.save(new CarModel(3l, "BMW", "E3śmieć", BigDecimal.valueOf(124), BigDecimal.valueOf(500), BodyType.COUPE, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.AVAILABLE, "Black", 45000, 2019));
        var car4 = carRepository.save(new CarModel(4l, "AUDI", "80", BigDecimal.valueOf(123), BigDecimal.valueOf(500), BodyType.SEDAN, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.AVAILABLE, "Black", 45000, 2019));

        var employee1 = employeeRepository.save(new EmployeeModel(1l, "jan", "kowalski", "saaaa"));
        var employee2 = employeeRepository.save(new EmployeeModel(2l, "karol", "nowak", "saaaa"));

        var customer1 = customerRepository.save(new CustomerModel(1l, "Janusz", "ChceszWMorde", "123456789", "qwerty", "janusz@gmail.com", "99122402212", "Pcim", "32-432"));

        returnRepository.save(new ReturnModel(1l, 2l, LocalDate.of(2023, 07, 16), 3l, "bez uszkodzeń"));
        rentalRepository.save(new RentalModel(1l, 1l, LocalDate.of(2023, 07, 16), 1l, "blablabla"));
        reservationRepository.save(new ReservationModel(1l, car1, LocalDate.of(2023, 7, 20), LocalDate.of(2023, 7, 25), BigDecimal.valueOf(0), "Kraków", "Kraków", customer1));
        reservationRepository.save(new ReservationModel(2l, car2, LocalDate.of(2023, 8, 20), LocalDate.of(2023, 8, 25), BigDecimal.valueOf(0), "Kraków", "Katowice",customer1));
        reservationRepository.save(new ReservationModel(3l, car3, LocalDate.of(2023, 6, 20), LocalDate.of(2023, 6, 25), BigDecimal.valueOf(0), "Kraków", "Warszawa",customer1));
        reservationRepository.save(new ReservationModel(4l, car4, LocalDate.of(2023, 9, 20), LocalDate.of(2023, 9, 25), BigDecimal.valueOf(0), "Katowice", "Katowice",customer1));
        companyRepository.save(new CompanyModel(1l, "SDACarRental", "SDACarRentalDomain", "SDACarRentalAddress", "SDACarRentalOwner", "SDACarRentalLogotype"));

    }
}
