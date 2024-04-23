package com.example.rentalcars.controller;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DepartmentRestController {

    private final DepartmentService departmentService;

//    @GetMapping("departments")
//    public List<DepartmentDTO> get() {
//        return departmentService.getDepartmentList();
//
//    }

    @PostMapping("/addDepartment")
    public void add(DepartmentModel departmentModel) {
        departmentService.addDepartment(departmentModel);
    }

    @PostMapping("/editDepartment/{id}")
    public void edit(DepartmentModel departmentModel) {
        departmentService.updateDepartment(departmentModel);
    }

    @PostMapping("removeDepartment/{id}")
    public void remove(@PathVariable("id") Long id) {
        departmentService.deleteDepartmentById(id);
    }


//    @GetMapping("/getDepartmentEmployees/{id}")
//    public Set<EmployeeDto> getDepartmentEmployees(@PathVariable("id") Long id) {
//        return departmentService.getDepartmentEmployees(id);
//    }

    @GetMapping("/getAllCarsByDepartment/{id}")
    public List<CarModel> getAllCarsByDepartment(@PathVariable("id") Long departmentId) {
        return departmentService.getAllCarsByDepartment(departmentId);
    }
}
