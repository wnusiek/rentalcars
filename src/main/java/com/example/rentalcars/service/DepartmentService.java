package com.example.rentalcars.service;

import com.example.rentalcars.DTO.DepartmentDTO;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.EmployeeModel;
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

    public void postAddDepartment(DepartmentModel department) {
        departmentRepository.save(department);
    }

    public List<DepartmentDTO> getDepartmentList() {
        return departmentRepository.findAll().stream().map(i -> new DepartmentDTO(i.getCity())).toList();
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

//    public List<CarModel> getAvailableCarsByDepartment(String city) {
//        return getDepartmentList().stream()
//                .filter(department -> department.getCity().equals(city))
//                .findFirst().get().getCars();
//    }

    public Set<EmployeeModel> getDepartmentEmployees(Long department_id){
        return departmentRepository.findAll().stream()
                .filter(department -> department.getId() != null && department.getId().equals(department_id))
                .map(DepartmentModel::getEmployees)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }
}
