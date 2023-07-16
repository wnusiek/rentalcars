package com.example.rentalcars.controller;

import com.example.rentalcars.model.EmployeeModel;
import com.example.rentalcars.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

//    @GetMapping("/employee")
//    public String getEmployeeList(Model model){
//        List<EmployeeModel> list = employeeService.getEmployeeList();
//        model.addAttribute("employeeModel", list);
//        return "employee/employee";
//    }

    @GetMapping("/addEmployee")
    public String getAddEmployee(){
        return "employee/addEmployee";
    }

    @PostMapping("/addEmployee")
        public RedirectView postAddEmployee(EmployeeModel employee){
        employeeService.postAddEmployee(employee);
        return new RedirectView("/employee");
    }

    @GetMapping("/editEmployee/{id}")
    public String getEditEmployee(@PathVariable("id") Long id, Model model){
        EmployeeModel employee = employeeService.findById(id);
        model.addAttribute("employeeModel", employee);
        return "employee/editEmployee";
    }

    @PostMapping("/editEmployee/{id}")
    public RedirectView postEditEmployee(EmployeeModel employee){
        employeeService.updateEmployee(employee);
        return new RedirectView("/employee");
    }

    @PostMapping("/removeEmployee/{id}")
    public RedirectView postRemoveEmployee(@PathVariable("id") Long id){
        employeeService.removeEmployee(id);
        return new RedirectView("/employee");
    }


}
