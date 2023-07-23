package com.example.rentalcars.service;

import com.example.rentalcars.DTO.EmployeeDto;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public void postAddEmployee(EmployeeModel employee) {
        employeeRepository.save(employee);
    }

    public List<EmployeeDto> getEmployeeList() {
        return employeeRepository.findAll().stream().map(i -> new EmployeeDto(i.getName())).toList();
    }

    public EmployeeModel findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(null);
    }

    public void updateEmployee(EmployeeModel employee) {
        employeeRepository.save(employee);
    }

    public void removeEmployee(Long id) {
        employeeRepository.deleteById(id);
    }


}

