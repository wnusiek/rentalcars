package com.example.rentalcars.controller;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/department")
public class DepartmentRestController {
    private final DepartmentService departmentService;

    @GetMapping("department")
    public List<DepartmentModel> get() {
        return departmentService.getDepartmentList();

    }
    @PostMapping("/addDepartment")
    public void add (DepartmentModel departmentModel) {
        departmentService.postAddDepartment(departmentModel);
    }

    @PostMapping ("/editDepartment/{id}")
    public void edit(DepartmentModel departmentModel) {
        departmentService.updateDepartment(departmentModel);
    }

    @PostMapping ("removeDepartment/{id}")
    public void remove (@PathVariable("id") Long id) {
        departmentService.removeDepartment(id);
    }

    @GetMapping("/availableCarsByDepartment")
    public List<CarModel> getAvailableCarsByDepartment(String city){
        return departmentService.getAvailableCarsByDepartment(city);
    }
}
