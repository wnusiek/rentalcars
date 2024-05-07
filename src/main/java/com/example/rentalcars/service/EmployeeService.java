package com.example.rentalcars.service;

import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeeModel> getEmployeeList() {
        return employeeRepository.findAll();
    }

    public void saveEmployee(EmployeeModel employeeModel) {
        if (employeeModel == null) {
            System.err.println("Employee is null.");
            return;
        }
        employeeRepository.save(employeeModel);
    }

    public void deleteEmployee(EmployeeModel employeeModel) {
        employeeRepository.delete(employeeModel);
    }

    public EmployeeModel getEmployeeByUserName(String userName) {
        var employee = getEmployeeList().stream().filter(employeeModel -> employeeModel.getUser().getName().equals(userName)).findFirst();
        if (employee.isPresent())
            return employee.get();
        else {
            System.err.println("Nie ma takiego pracownika");
            return null;
        }

    }


}

