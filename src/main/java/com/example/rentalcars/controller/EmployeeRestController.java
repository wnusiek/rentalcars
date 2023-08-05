package com.example.rentalcars.controller;

import com.example.rentalcars.DTO.EmployeeDto;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeRestController {

    private final EmployeeService employeeService;

//    @GetMapping("employees")
//    public List<EmployeeModel> get() {
//        return employeeService.getEmployeeList();
//    }

//    @PostMapping("/addEmployee")
//    public void add(EmployeeModel employee) {
//        employeeService.postAddEmployee(employee);
//    }
//
//    @PostMapping("/editEmployee/{id}")
//    public void edit(EmployeeModel employee) {
//        employeeService.updateEmployee(employee);
//    }
//
//    @PostMapping("/removeEmployee/{id}")
//    public void delete(@PathVariable("id") Long id) {
//        employeeService.removeEmployee(id);
//    }

}

