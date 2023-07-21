package com.example.rentalcars.controllers_old;

import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.service.DepartmentService;
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
public class DepartmentController {

    private final DepartmentService departmentService;

//    @GetMapping("/department")
//    public String getDepartmentList(Model model) {
//        List<DepartmentModel> list = departmentService.getDepartmentList();
//        model.addAttribute("departmentModel", list);
//        return "department/department";
//    }

   /* @GetMapping("/addDepartment")
    public String getAddDepartment(){
        return "department/addDepartment";
    }

    @PostMapping("/addDepartment")
    public RedirectView postAddDepartment(DepartmentModel department) {
        departmentService.postAddDepartment(department);
        return new RedirectView("/department");
    }

    @GetMapping("/editDepartment/{id}")
    public String getEditDepartment(@PathVariable("id") Long id, Model model) {
        DepartmentModel department = departmentService.findById(id);
        model.addAttribute("departmentModel", department);
        return "department/editDepartment";
    }

    @PostMapping("/editDepartment/{id}")
    public RedirectView postEditDepartment(DepartmentModel department){
        departmentService.updateDepartment(department);
        return new RedirectView("/department");
    }

    @PostMapping("/removeDepartment/{id}")
    public RedirectView removeDepartment(@PathVariable("id") Long id) {
        departmentService.removeDepartment(id);
        return new RedirectView("/department");
    }*/
}
