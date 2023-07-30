package com.example.rentalcars.ManagmentControllers;

import com.example.rentalcars.repository.DepartmentRepository;
import com.example.rentalcars.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmployeeManagementController {
    private final EmployeeRepository employeeRepository;

    private final DepartmentRepository departmentRepository;

//    @PostMapping("/addEmployeeToDepartment/{employeeId}/{departmentId}")
//    public void addEmployeeToDepartment(@PathVariable Long employeeId, @PathVariable Long departmentId){
//
//        var employee = employeeRepository.findById(employeeId);
//        var department = departmentRepository.findById(departmentId);
//        if (employee.isPresent() && department.isPresent()){
//            var e = employee.get();
//            var d = department.get();
//            d.getEmployees().add(e);
//            departmentRepository.save(d);
//        }
//    }
}
