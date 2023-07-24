package com.example.rentalcars.controller;

import com.example.rentalcars.DTO.CarDto;
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
    public List<CarDto> get() {
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
    public List<CarDto> getAvailableCars() {
        return carService.getAvailableCars();
    }

    @PostMapping("/setCarStatus/{id}/{carStatus}")
    public void setCarStatus(@PathVariable Long id, @PathVariable CarStatus carStatus) {
        carService.setCarStatus(id, carStatus);
    }

    @GetMapping("/carsByGearbox")
    public List<CarDto> getCarsByGearbox(GearboxType gearbox) {
        return carsBySpecificationService.getCarsByGearbox(gearbox);
    }

    @GetMapping("/carsByMark")
    public List<CarDto> getCarsByMark(String mark) {
        return carsBySpecificationService.getCarsByMark(mark);
    }

    @GetMapping("carsByFuelType")
    public List<CarDto> getCarsByFuelType(FuelType fuelType) {
        return carsBySpecificationService.getCarsByFuelType(fuelType);
    }

    @GetMapping("/sortCarsByPriceAscending")
    public List<CarDto> getCarsByPriceAscending() {
        return carsByPriceService.getCarsByPriceAscending();
    }

    @GetMapping("/sortCarsByPriceDescending")
    public List<CarDto> getCarsByPriceDescending() {
        return carsByPriceService.getCarsByPriceDescending();
    }

    @GetMapping("/sortCarsByPriceRange")
    public List<CarDto> getCarsByPriceRange(BigDecimal priceMin, BigDecimal priceMax) {
        return carsByPriceService.getCarsByPriceRange(priceMin, priceMax);
    }

    @GetMapping("/getCarsByBodyType")
    public List<CarDto> getCarsByBodyType(BodyType bodyType) {
        return carsBySpecificationService.getCarsByBodyType(bodyType);
    }

    @GetMapping("/sortCarByUpperPriceBorder")
    public List<CarDto> getCarsCheaperThan(BigDecimal upperPriceBorder) {
        return carsByPriceService.getCarsCheaperThan(upperPriceBorder);
    }

    @GetMapping("/sortCarByLowerPriceBorder")
    public List<CarDto> getCarsMoreExpensiveThan(BigDecimal lowerPriceBorder) {
        return carsByPriceService.getCarsMoreExpensiveThan(lowerPriceBorder);
    }

    @GetMapping("/getCarsWithHigherMileageThan")
    public List<CarDto> getCarsWithHigherMileageThan(Integer mileage) {
        return carsByMileageService.getCarsWithHigherMileageThan(mileage);
    }

    @GetMapping("/getCarsWithLowerMileageThan")
    public List<CarDto> getCarsWithLowerMileageThan(Integer mileage) {
        return carsByMileageService.getCarsWithLowerMileageThan(mileage);
    }

    @GetMapping("getCarsWithinGivenMileage")
    public List<CarDto> getCarsWithinGivenMileage(Integer minMileage, Integer maxMileage) {
        return carsByMileageService.getCarsWithinGivenMileage(minMileage, maxMileage);
    }

    @GetMapping("getCarsByColor")
    public List<CarDto> getCarsByColor(String color) {
        return carsBySpecificationService.getCarsByColor(color);
    }

    @GetMapping("getCarsByProductionDate")
    public List<CarDto> getCarsByProductionDate(Integer productionDate) {
        return carsByDateService.getCarsByProductionDate(productionDate);
    }

    @GetMapping("/getCarsNewerThan")
    public List<CarDto> getCarsNewerThan(Integer productionDate) {
        return carsByDateService.getCarsNewerThan(productionDate);
    }

    @GetMapping("/getCarsOlderThan")
    public List<CarDto> getCarsOlderThan(Integer productionDate) {
        return carsByDateService.getCarsOlderThan(productionDate);
    }
}
