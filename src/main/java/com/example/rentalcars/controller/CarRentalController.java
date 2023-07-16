package com.example.rentalcars.controller;
import com.example.rentalcars.model.DepartmentModel;
import com.example.rentalcars.service.CarRentalService;
import com.example.rentalcars.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/testCarRentalController")
public class CarRentalController {
    private final CarRentalService carRentalService;
    @GetMapping
    public List<DepartmentModel> get(){
        return carRentalService.getCarRentalModelList();

    }
}
