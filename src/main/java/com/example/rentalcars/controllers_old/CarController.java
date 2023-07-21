package com.example.rentalcars.controllers_old;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;


@RequiredArgsConstructor
@RestController
//@RequestMapping("/car")
public class CarController {

    private final CarService carService;


//    @GetMapping
//    public List<CarModel> getCarList() {
////        List<CarModel> list = carService.getCarList();
////        model.addAttribute("carModel", list);
//        return carService.getCarList();
    }
//
//    @GetMapping("/addCar")
//    public String getAddCar(){
//        return "car/addCar";
//    }
//
//    @PostMapping("/addCar")
//    public RedirectView postAddCar(CarModel car) {
//        carService.postAddCar(car);
//        return new RedirectView("/car");
//    }
//
//    @GetMapping("/editCar/{id}")
//    public String getEditCar(@PathVariable("id") Long id, Model model) {
//        CarModel car = carService.findById(id);
//        model.addAttribute("carModel", car);
//        return "car/editCar";
//    }
//
//    @PostMapping("/editCar/{id}")
//    public RedirectView postEditCar(CarModel car){
//        carService.updateCar(car);
//        return new RedirectView("/car");
//    }
//
//    @PostMapping("/removeCar/{id}")
//    public RedirectView removeCar(@PathVariable("id") Long id) {
//        carService.removeCar(id);
//        return new RedirectView("/car");
//    }
//}
