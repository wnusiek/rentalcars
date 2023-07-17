package com.example.rentalcars.controller;

import com.example.rentalcars.model.CarRentalModel;
import com.example.rentalcars.service.CarRentalService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
//@RequestMapping("/CarRental")
public class CarRentalRestController {

    private final CarRentalService carRentalService;

    @GetMapping("/carRental")
    public List<CarRentalModel> get() {
        return carRentalService.getCarRentalModelList();
    }

    @PostMapping ("/addCarRental")
    public void add (CarRentalModel carRentalModel) {
        carRentalService.postAddCarRental(carRentalModel);
    }

    @PostMapping ("/editCarRental/{id}")
    public void edit(CarRentalModel carRentalModel) {
        carRentalService.updateCarRental(carRentalModel);
    }

    @PostMapping ("removeCarRental/{id}")
    public void removeCarRental (@PathVariable("id") Long id) {
        carRentalService.removeCarRental(id);
    }



}
