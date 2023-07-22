package com.example.rentalcars.service;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.repository.DepartmentRepository;
import com.example.rentalcars.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    public void postAddDepartment(DepartmentModel department) {
        departmentRepository.save(department);
    }

    public List<DepartmentModel> getDepartmentList() {
        return departmentRepository.findAll();
    }

    public DepartmentModel findById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public void updateDepartment(DepartmentModel department) {
        departmentRepository.save(department);
    }

    public void removeDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public List<CarModel> getAvailableCarsByDepartment(String city) {
        return getDepartmentList().stream()
                .filter(department -> department.getCity().equals(city))
                .findFirst().get().getCars();
    }


}
