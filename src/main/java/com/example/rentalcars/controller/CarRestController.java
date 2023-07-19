package com.example.rentalcars.controller;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
//@RequestMapping("/car")
public class CarRestController {

    private final CarService carService;

    @GetMapping("/car")
    public List<CarModel> get(){
        return carService.getCarList();
    }

    @PostMapping("/addCar")
    public void add(CarModel car){
        carService.postAddCar(car);
    }

    @PostMapping("/editCar/{id}")
    public void edit(CarModel car){
        carService.updateCar(car);
    }

    @PostMapping("/removeCar/{id}")
    public void remove(@PathVariable("id")Long id){
        carService.removeCar(id);
    }

    @GetMapping("/availableCars")
    public List<CarModel> getAvailableCars(){
        return carService.getAvailableCars();
    }

    @GetMapping("/carsByGearbox")
    public List<CarModel> getCarsByGearbox(String gearbox){
        return carService.getCarsByGearbox(gearbox);
    }

    @GetMapping("carsByFuelType")
    public List<CarModel> getCarsByFuelType(String fuelType){
        return carService.getCarsByFuelType(fuelType);
    }
      
    @GetMapping("/sortCarsByPriceAscending")
    public List<CarModel> getCarsByGearbox(){
        return carService.getCarsByPriceAscending();
    }
}
