package com.example.rentalcars.controller;

import com.example.rentalcars.DTO.EmployeeDto;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/employee")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @GetMapping("employees")
    public List<EmployeeDto> get() {
        return employeeService.getEmployeeList();
    }

    @PostMapping("/addEmployee")
    public void add(EmployeeModel employee) {
        employeeService.postAddEmployee(employee);
    }

    @PostMapping("/editEmployee/{id}")
    public void edit(EmployeeModel employee) {
        employeeService.updateEmployee(employee);
    }

    @PostMapping("/removeEmployee/{id}")
    public void delete(@PathVariable("id") Long id) {
        employeeService.removeEmployee(id);
    }

}

