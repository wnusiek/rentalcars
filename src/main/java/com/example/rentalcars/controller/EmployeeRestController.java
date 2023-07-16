package com.example.rentalcars.controller;

import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.repository.EmployeeRepository;
import com.example.rentalcars.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/empolyee")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeModel> get(){
        return employeeService.getEmployeeList();
    }
}
