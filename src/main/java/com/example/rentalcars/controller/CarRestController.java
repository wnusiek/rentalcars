package com.example.rentalcars.controller;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/car")
public class CarRestController {

    private final CarService carService;

    @GetMapping
    public List<CarModel> get(){
        return carService.getCarList();
    }

    @PostMapping("/addCar")
    public void add(CarModel car){
        carService.postAddCar(car);
    }
}
