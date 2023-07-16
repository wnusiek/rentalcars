package com.example.rentalcars;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.repository.CarRepository;
import com.example.rentalcars.repository.DepartmentRepository;
import com.example.rentalcars.repository.EmployeeRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

@Component
public class DbInitializer implements ApplicationRunner {
    private final CarRepository carRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public DbInitializer(CarRepository carRepository, EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.carRepository = carRepository;
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        carRepository.save(new CarModel(1l, "Opel", "Corsa", BigDecimal.ONE, BigDecimal.valueOf(500), "Sedan", "automatyczna", 5, 4, "benzyna", "aaa", true));
        var employee1 = employeeRepository.save(new EmployeeModel(1l, "jan", "kowalski", "saaaa"));
        departmentRepository.save((new DepartmentModel(1l, "lublin", Set.of(employee1))));
    }
}
