package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.CustomerAdditionException;
import com.example.rentalcars.Exceptions.EmployeeAdditionException;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeeModel> getEmployeeList() {
        return employeeRepository.findAll();
    }

    public EmployeeModel saveEmployee(EmployeeModel employeeModel) {
        try {
            EmployeeModel savedEmployee = employeeRepository.save(employeeModel);
            System.out.println("Pracownik został dodany pomyślnie");
            return savedEmployee;
        } catch (Exception e) {
            throw new EmployeeAdditionException("Błąd podczas dodawania pracownika", e);
        }
    }

    public void deleteEmployee(EmployeeModel employeeModel) {
        employeeRepository.delete(employeeModel);
    }

    public EmployeeModel getEmployeeByUserName(String userName) {
        var employeeModel = getEmployeeList().stream().filter(e -> e.getUser().getName().equals(userName)).findFirst();
        return employeeModel.orElseThrow(() -> new EntityNotFoundException("Nie ma takiego pracownika"));
    }

    public EmployeeModel findById(Long id) {
        Optional<EmployeeModel> employeeModel = employeeRepository.findById(id);
        return employeeModel.orElseThrow(() -> new EntityNotFoundException("Nie znaleziono pracownika o id = " + id));
    }
}

