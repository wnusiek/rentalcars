package com.example.rentalcars.service;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.repository.CarRepository;
import com.example.rentalcars.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class DepartmentService {

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
}
