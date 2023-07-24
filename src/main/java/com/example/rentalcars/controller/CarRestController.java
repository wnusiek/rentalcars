package com.example.rentalcars.controller;

import com.example.rentalcars.MenagementServices.CarsByDateService;
import com.example.rentalcars.MenagementServices.CarsByMileageService;
import com.example.rentalcars.MenagementServices.CarsBySpecificationService;
import com.example.rentalcars.enums.BodyType;
import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.enums.FuelType;
import com.example.rentalcars.enums.GearboxType;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.MenagementServices.CarsByPriceService;
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

    private final CarsByPriceService carsByPriceService;

    private final CarsBySpecificationService carsBySpecificationService;

    private final CarsByMileageService carsByMileageService;

    private final CarsByDateService carsByDateService;

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
        return carsBySpecificationService.getCarsByGearbox(gearbox);
    }

    @GetMapping("/carsByMark")
    public List<CarModel> getCarsByMark(String mark) {
        return carsBySpecificationService.getCarsByMark(mark);
    }

    @GetMapping("carsByFuelType")
    public List<CarModel> getCarsByFuelType(FuelType fuelType) {
        return carsBySpecificationService.getCarsByFuelType(fuelType);
    }

    @GetMapping("/sortCarsByPriceAscending")
    public List<CarModel> getCarsByPriceAscending() {
        return carsByPriceService.getCarsByPriceAscending();
    }

    @GetMapping("/sortCarsByPriceDescending")
    public List<CarModel> getCarsByPriceDescending() {
        return carsByPriceService.getCarsByPriceDescending();
    }

    @GetMapping("/sortCarsByPriceRange")
    public List<CarModel> getCarsByPriceRange(BigDecimal priceMin, BigDecimal priceMax) {
        return carsByPriceService.getCarsByPriceRange(priceMin, priceMax);
    }

    @GetMapping("/getCarsByBodyType")
    public List<CarModel> getCarsByBodyType(BodyType bodyType) {
        return carsBySpecificationService.getCarsByBodyType(bodyType);
    }

    @GetMapping("/sortCarByUpperPriceBorder")
    public List<CarModel> getCarsCheaperThan(BigDecimal upperPriceBorder) {
        return carsByPriceService.getCarsCheaperThan(upperPriceBorder);
    }

    @GetMapping("/sortCarByLowerPriceBorder")
    public List<CarModel> getCarsMoreExpensiveThan(BigDecimal lowerPriceBorder) {
        return carsByPriceService.getCarsMoreExpensiveThan(lowerPriceBorder);
    }

    @GetMapping("/getCarsWithHigherMileageThan")
    public List<CarModel> getCarsWithHigherMileageThan(Integer mileage) {
        return carsByMileageService.getCarsWithHigherMileageThan(mileage);
    }

    @GetMapping("/getCarsWithLowerMileageThan")
    public List<CarModel> getCarsWithLowerMileageThan(Integer mileage) {
        return carsByMileageService.getCarsWithLowerMileageThan(mileage);
    }

    @GetMapping("getCarsWithinGivenMileage")
    public List<CarModel> getCarsWithinGivenMileage(Integer minMileage, Integer maxMileage) {
        return carsByMileageService.getCarsWithinGivenMileage(minMileage, maxMileage);
    }

    @GetMapping("getCarsByColor")
    public List<CarModel> getCarsByColor(String color) {
        return carsBySpecificationService.getCarsByColor(color);
    }

    @GetMapping("getCarsByProductionDate")
    public List<CarModel> getCarsByProductionDate(Integer productionDate) {
        return carsByDateService.getCarsByProductionDate(productionDate);
    }

    @GetMapping("/getCarsNewerThan")
    public List<CarModel> getCarsNewerThan(Integer productionDate) {
        return carsByDateService.getCarsNewerThan(productionDate);
    }

    @GetMapping("/getCarsOlderThan")
    public List<CarModel> getCarsOlderThan(Integer productionDate) {
        return carsByDateService.getCarsOlderThan(productionDate);
    }
}
