package com.example.rentalcars;

import com.example.rentalcars.enums.*;
import com.example.rentalcars.model.*;
import com.example.rentalcars.repository.*;
import com.example.rentalcars.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DbInitializer implements ApplicationRunner {

    private final CarRepository carRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;
    private final DepartmentService departmentService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        departmentRepository.save((new DepartmentModel(1l, "Lublin")));
        departmentRepository.save((new DepartmentModel(2l, "Kraków")));
        departmentRepository.save((new DepartmentModel(3l, "Katowice")));
        departmentRepository.save((new DepartmentModel(4l, "Warszawa")));


        carRepository.save(new CarModel(1L, "Audi", "Quattro", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.COUPE, GearboxType.MANUAL, 2, 2, FuelType.PETROL, "Nie", CarStatus.AVAILABLE, "White", 200000, 1980, null));
        carRepository.save(new CarModel(2L, "Peugeot", "205 GTI", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.HATCHBACK, GearboxType.MANUAL, 5, 3, FuelType.PETROL, "Nie", CarStatus.AVAILABLE, "Black", 200000, 1984, null));
        carRepository.save(new CarModel(3L, "Renault", "Clio", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.HATCHBACK, GearboxType.MANUAL, 5, 3, FuelType.PETROL, "Nie", CarStatus.AVAILABLE, "Blue", 200000, 1995, null));
        carRepository.save(new CarModel(4L, "Fiat", "Punto", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.HATCHBACK, GearboxType.MANUAL, 5, 3, FuelType.PETROL, "Nie", CarStatus.AVAILABLE, "Red", 200000, 1998, null));
        carRepository.save(new CarModel(5L, "Volkswagen", "Golf", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.HATCHBACK, GearboxType.MANUAL, 5, 3, FuelType.PETROL, "Nie", CarStatus.AVAILABLE, "Blue", 200000, 1974, "volkswagen_golf.jpg"));
        carRepository.save(new CarModel(6L, "Ford", "Mustang", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.COUPE, GearboxType.AUTOMATIC, 4, 2, FuelType.PETROL, "Nie", CarStatus.AVAILABLE, "Red", 222000, 1972, "ford_mustang.jpg"));
        carRepository.save(new CarModel(7L, "Toyota", "Corolla", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.SEDAN, GearboxType.MANUAL, 5, 4, FuelType.PETROL, "Nie", CarStatus.AVAILABLE, "White", 200000, 1980, null));
        carRepository.save(new CarModel(8L, "Chevrolet", "Camaro", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.COUPE, GearboxType.MANUAL, 4, 2, FuelType.PETROL, "Nie", CarStatus.AVAILABLE, "Black", 200000, 1978, null));
        carRepository.save(new CarModel(9L, "Honda", "Civic", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.HATCHBACK, GearboxType.MANUAL, 5, 3, FuelType.PETROL, "Nie", CarStatus.AVAILABLE, "Silver", 200000, 1995, null));
        carRepository.save(new CarModel(10L, "BMW", "3 Series", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.SEDAN, GearboxType.MANUAL, 5, 4, FuelType.PETROL, "Nie", CarStatus.AVAILABLE, "Grey", 200000, 1985, null));
        carRepository.save(new CarModel(11L, "Mercedes-Benz", "E-Class", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.SEDAN, GearboxType.AUTOMATIC, 5, 4, FuelType.DIESEL, "Nie", CarStatus.AVAILABLE, "Green", 200000, 1990, null));
        carRepository.save(new CarModel(12L, "Nissan", "Patrol", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.SUV, GearboxType.MANUAL, 5, 4, FuelType.PETROL, "Nie", CarStatus.AVAILABLE, "Yellow", 200000, 1988, null));
        carRepository.save(new CarModel(13L, "Jeep", "Wrangler", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.SUV, GearboxType.MANUAL, 4, 2, FuelType.PETROL, "Nie", CarStatus.AVAILABLE, "Brown", 200000, 1997, null));
        carRepository.save(new CarModel(14L, "Mazda", "MX-5 Miata", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.CONVERTIBLE, GearboxType.MANUAL, 2, 2, FuelType.PETROL, "Nie", CarStatus.AVAILABLE, "Orange", 200000, 1992, null));
        carRepository.save(new CarModel(15L, "Subaru", "Impreza", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.SEDAN, GearboxType.MANUAL, 5, 4, FuelType.PETROL, "Nie", CarStatus.AVAILABLE, "Blue", 200000, 1998, null));
        carRepository.save(new CarModel(16L, "Ferrari", "F40", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.COUPE, GearboxType.MANUAL, 2, 2, FuelType.PETROL, "Nie", CarStatus.AVAILABLE, "Red", 200000, 1987, null));
        carRepository.save(new CarModel(17L, "Lamborghini", "Diablo", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.COUPE, GearboxType.MANUAL, 2, 2, FuelType.PETROL, "Nie", CarStatus.AVAILABLE, "Yellow", 200000, 1990, null));
        carRepository.save(new CarModel(18L, "Porsche", "911", BigDecimal.valueOf(200), BigDecimal.valueOf(100), BodyType.COUPE, GearboxType.MANUAL, 2, 2, FuelType.PETROL, "Nie", CarStatus.AVAILABLE, "Silver", 200000, 1985, null));


        departmentService.addCarToDepartment(1l,1l);
        departmentService.addCarToDepartment(2l,1l);
        departmentService.addCarToDepartment(3l,1l);
        departmentService.addCarToDepartment(4l,1l);

        departmentService.addCarToDepartment(5l,2l);
        departmentService.addCarToDepartment(6l,2l);
        departmentService.addCarToDepartment(7l,2l);
        departmentService.addCarToDepartment(8l,2l);

        departmentService.addCarToDepartment(9l,3l);
        departmentService.addCarToDepartment(10l,3l);
        departmentService.addCarToDepartment(11l,3l);
        departmentService.addCarToDepartment(12l,3l);

        departmentService.addCarToDepartment(13l,4l);
        departmentService.addCarToDepartment(14l,4l);
        departmentService.addCarToDepartment(15l,4l);
        departmentService.addCarToDepartment(16l,4l);

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

        departmentService.addEmployeeToDepartment(1l,1l);

        companyRepository.save(new CompanyModel(1l, "Gruz-rental", "gruz-rental.com", "", "Janusz", ""));

    }
}
