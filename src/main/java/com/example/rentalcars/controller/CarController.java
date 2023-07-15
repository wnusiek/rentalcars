package com.example.rentalcars.controller;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.service.CarService;
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
public class CarController {

    private final CarService carService;

    @GetMapping("/car")
    public String getCarList(Model model) {
        List<CarModel> list = carService.getCarList();
        model.addAttribute("carModel", list);
        return "car/car";
    }

    @GetMapping("/addCar")
    public String getAddCar(){
        return "car/addCar";
    }

    @PostMapping("/addCar")
    public RedirectView postAddCar(CarModel car) {
        carService.postAddCar(car);
        return new RedirectView("/car");
    }

    @GetMapping("/editCar/{id}")
    public String getEditCar(@PathVariable("id") Long id, Model model) {
        CarModel car = carService.findById(id);
        model.addAttribute("carModel", car);
        return "car/editCar";
    }

    @PostMapping("/edit/{id}")
    public RedirectView postEditCar(CarModel car){
        carService.updateCar(car);
        return new RedirectView("/car");
    }

    @PostMapping("/removeCar/{id}")
    public RedirectView removeCar(@PathVariable("id") Long id) {
        carService.removeCar(id);
        return new RedirectView("/car");
    }
}
