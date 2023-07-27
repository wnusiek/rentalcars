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


//    public List<EmployeeDto> getEmployeeList() {
//    return employeeRepository.findAll().stream().map(i -> new EmployeeDto(i.getName(), i.getSurname(), i.getPosition())).toList();
//    }

    public List<EmployeeModel> getEmployeeList() {
        return employeeRepository.findAll();
    }

    public void saveEmployee(EmployeeModel employeeModel){
        if (employeeModel == null ){
            System.err.println("Employee is null.");
            return;
        }
        employeeRepository.save(employeeModel);
    }

    public void deleteEmployee(EmployeeModel employeeModel){
        employeeRepository.delete(employeeModel);
    }


}

