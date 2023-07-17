package com.example.rentalcars.controller;

import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.model.ReservationModel;
import com.example.rentalcars.model.ReturnModel;
import com.example.rentalcars.service.ReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/return")
public class ReturnRestController {

    private final ReturnService returnService;

    @GetMapping("/return")
    public List<ReturnModel> get() {
        return returnService.getReturnModelList();
    }

    @PostMapping("/addReturn")
    public void add(ReturnModel returnModel) {
        returnService.addReturn(returnModel);
    }

    @PostMapping("/editReturn/{id}")
    public void edit(ReturnModel returnModel) {
        returnService.updateReturn(returnModel);
    }

    @PostMapping("removeReturn/{id}")
    public void removeReturn(@PathVariable("id") Long id){
        returnService.deleteReturn(id);
    }
}