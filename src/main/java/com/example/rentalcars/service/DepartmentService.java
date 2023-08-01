package com.example.rentalcars.service;

import com.example.rentalcars.DTO.DepartmentDTO;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.repository.CarRepository;
import com.example.rentalcars.repository.DepartmentRepository;
import com.example.rentalcars.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

    private final CarRepository carRepository;

    public void postAddDepartment(DepartmentModel department) {
        departmentRepository.save(department);
    }

    public List<DepartmentModel> getDepartmentList1() {
        return departmentRepository.findAll();
    }
    public List<DepartmentDTO> getDepartmentList() {
        return departmentRepository.findAll().stream().map(i -> new DepartmentDTO(i.getId(), i.getCity(), i.getCars())).toList();
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

    public void deleteDepartment(DepartmentModel departmentModel){
        departmentRepository.delete(departmentModel);
    }

    public Set<EmployeeModel> getDepartmentEmployees(Long departmentId) {
        return departmentRepository.findAll().stream()
                .filter(department -> department.getId() != null && department.getId().equals(departmentId))
                .map(DepartmentModel::getEmployees)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public List<CarModel> getAllCarsByDepartment(Long departmentId) {
        return departmentRepository.findAll().stream()
                .filter(department -> department.getId() != null && department.getId().equals(departmentId))
                .map(DepartmentModel::getCars)
                .flatMap(List::stream)
                .collect(Collectors.toList());

    }

}
