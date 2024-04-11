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

    private final RoleRepository roleRepository;

    public DbInitializer(CarRepository carRepository, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, ReturnRepository returnRepository, RentalRepository rentalRepository, ReservationRepository reservationRepository, CustomerRepository customerRepository, CompanyRepository companyRepository, DepartmentService departmentService, UserRepository userRepository, RoleRepository roleRepository) {
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
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        departmentRepository.save((new DepartmentModel(1l, "Lublin")));
        departmentRepository.save((new DepartmentModel(2l, "Kraków")));
        departmentRepository.save((new DepartmentModel(3l, "Katowice")));
        departmentRepository.save((new DepartmentModel(4l, "Warszawa")));

        carRepository.save(new CarModel(1l, "Opel", "Corsa", BigDecimal.valueOf(100), BigDecimal.valueOf(500), BodyType.HATCHBACK, GearboxType.AUTOMATIC, 5, 4, FuelType.PETROL, "aaa", CarStatus.AVAILABLE, "Red", 128000, 2004));
        carRepository.save(new CarModel(2l, "Opel", "Astra", BigDecimal.valueOf(200), BigDecimal.valueOf(500), BodyType.SEDAN, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.AVAILABLE, "Black", 45000, 2019));
        carRepository.save(new CarModel(3l, "BMW", "E3śmieć", BigDecimal.valueOf(400), BigDecimal.valueOf(500), BodyType.COUPE, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.AVAILABLE, "Black", 45000, 2019));
        carRepository.save(new CarModel(4l, "AUDI", "80", BigDecimal.valueOf(300), BigDecimal.valueOf(500), BodyType.SEDAN, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "aaa", CarStatus.AVAILABLE, "Black", 45000, 2019));

        departmentService.addCarToDepartment(1l,1l);
        departmentService.addCarToDepartment(2l,2l);
        departmentService.addCarToDepartment(3l,3l);
        departmentService.addCarToDepartment(4l,4l);

        var roleAdmin = roleRepository.save(new RoleModel(1l, "ADMIN"));
        var roleManager = roleRepository.save(new RoleModel(2l, "MANAGER"));
        var roleEmployee = roleRepository.save(new RoleModel(3l, "EMPLOYEE"));
        var roleCustomer = roleRepository.save(new RoleModel(4l, "CUSTOMER"));

        var userAdmin = userRepository.save(new UserModel(1l, "admin","admin", "admin@wylacziwlacz.pl", true, roleAdmin));
        var userManager = userRepository.save(new UserModel(2l, "manager","manager", "manager@manager.pl", true, roleManager));
        var userEmployee = userRepository.save(new UserModel(3l, "employee","employee", "employee@employee.pl", true, roleEmployee));
        var userCustomer = userRepository.save(new UserModel(4l, "klient","klient", "klient@klient.pl", true, roleCustomer));

        employeeRepository.save(new EmployeeModel(1l, "admin", "admin", EmployeePosition.ADMIN, userAdmin));
        employeeRepository.save(new EmployeeModel(2l, "manager", "manager", EmployeePosition.MANAGER, userManager));
        employeeRepository.save(new EmployeeModel(3l, "employee", "employee", EmployeePosition.EMPLOYEE, userEmployee));
        customerRepository.save(new CustomerModel(1l, "klient", "klient", "000000000", "AAAAAA", "99999999999", "ZZZZZZZZ", "00-000", userCustomer));

        companyRepository.save(new CompanyModel(1l, "Gruz-rental", "gruz-rental.com", "", "", ""));

    }
}
