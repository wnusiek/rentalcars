package com.example.rentalcars.controller;

import com.example.rentalcars.enums.BodyType;
import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.enums.FuelType;
import com.example.rentalcars.enums.GearboxType;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CarRestController {

    private final CarService carService;

    @GetMapping("/cars")
    public List<CarModel> get() {
        return carService.getCarList();
    }

    @PostMapping("/addCar")
    public void add(CarModel car) {
        carService.postAddCar(car);
    }

    @PostMapping("/editCar/{id}")
    public void edit(CarModel car) {
        carService.updateCar(car);
    }

    @PostMapping("/removeCar/{id}")
    public void remove(@PathVariable("id") Long id) {
        carService.removeCar(id);
    }

    @GetMapping("/availableCars")
    public List<CarModel> getAvailableCars() {
        return carService.getAvailableCars();
    }

    @PostMapping("/setCarStatus/{id}/{carStatus}")
    public void setCarStatus(@PathVariable Long id, @PathVariable CarStatus carStatus) {
        carService.setCarStatus(id, carStatus);
    }

    @GetMapping("/carsByGearbox")
    public List<CarModel> getCarsByGearbox(GearboxType gearbox) {
        return carService.getCarsByGearbox(gearbox);
    }

    @GetMapping("/carsByMark")
    public List<CarModel> getCarsByMark(String mark) {
        return carService.getCarsByMark(mark);
    }

    @GetMapping("carsByFuelType")
    public List<CarModel> getCarsByFuelType(FuelType fuelType) {
        return carService.getCarsByFuelType(fuelType);
    }

    @GetMapping("/sortCarsByPriceAscending")
    public List<CarModel> getCarsByPriceAscending() {
        return carService.getCarsByPriceAscending();
    }

    @GetMapping("/sortCarsByPriceDescending")
    public List<CarModel> getCarsByPriceDescending() {
        return carService.getCarsByPriceDescending();
    }

    @GetMapping("/sortCarsByPriceRange")
    public List<CarModel> getCarsByPriceRange(BigDecimal priceMin, BigDecimal priceMax) {
        return carService.getCarsByPriceRange(priceMin, priceMax);
    }

    @GetMapping("/getCarsByBodyType")
    public List<CarModel> getCarsByBodyType(BodyType bodyType) {
        return carService.getCarsByBodyType(bodyType);
    }

    @GetMapping("/sortCarByUpperPriceBorder")
    public List<CarModel> getCarsCheaperThan(BigDecimal upperPriceBorder) {
        return carService.getCarsCheaperThan(upperPriceBorder);
    }

    @GetMapping("/sortCarByLowerPriceBorder")
    public List<CarModel> getCarsMoreExpensiveThan(BigDecimal lowerPriceBorder) {
        return carService.getCarsMoreExpensiveThan(lowerPriceBorder);
    }

    @GetMapping("/getCarsWithHigherMileageThan")
    public List<CarModel> getCarsWithHigherMileageThan(Integer mileage) {
        return carService.getCarsWithHigherMileageThan(mileage);
    }

    @GetMapping("/getCarsWithLowerMileageThan")
    public List<CarModel> getCarsWithLowerMileageThan(Integer mileage) {
        return carService.getCarsWithLowerMileageThan(mileage);
    }

    @GetMapping("getCarsWithinGivenMileage")
    public List<CarModel> getCarsWithinGivenMileage(Integer minMileage, Integer maxMileage) {
        return carService.getCarsWithinGivenMileage(minMileage, maxMileage);
    }

    @GetMapping("getCarsByColor")
    public List<CarModel> getCarsByColor(String color) {
        return carService.getCarsByColor(color);
    }

    @GetMapping("getCarsByProductionDate")
    public List<CarModel> getCarsByProductionDate(Integer productionDate) {
        return carService.getCarsByProductionDate(productionDate);
    }

    @GetMapping("/getCarsNewerThan")
    public List<CarModel> getCarsNewerThan(Integer productionDate) {
        return carService.getCarsNewerThan(productionDate);
    }

    @GetMapping("/getCarsOlderThan")
    public List<CarModel> getCarsOlderThan(Integer productionDate) {
        return carService.getCarsOlderThan(productionDate);
    }
}
