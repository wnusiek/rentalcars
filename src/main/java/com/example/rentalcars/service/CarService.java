package com.example.rentalcars.service;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.repository.CarRepository;
import com.example.rentalcars.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public void postAddCar(CarModel car) {
        carRepository.save(car);
    }

    public List<CarModel> getCarList() {
        return carRepository.findAll();
    }

    public CarModel findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    public void updateCar(CarModel car) {
        carRepository.save(car);
    }

    public void removeCar(Long id) {
        carRepository.deleteById(id);
    }


    public List<CarModel> getAvailableCars() {
        return getCarList().stream()
                .filter(car -> car.getAvailability() != null)
                .filter(car -> car.getAvailability() == true)
                .toList();
    }

    public List<CarModel> getCarsByGearbox(String gearbox) {
        return getCarList().stream()
                .filter(car -> car.getGearbox() != null)
                .filter(car -> car.getGearbox().equals(gearbox))
                .toList();
    }

    public List<CarModel> getCarsByMark(String mark) {
        return getCarList().stream()
                .filter(car -> car.getMark() != null)
                .filter(car -> car.getMark().equals(mark))
                .toList();
    }

    public List<CarModel> getCarsByFuelType(String fuelType) {
        return getCarList().stream()
                .filter(car -> car.getFuelType() != null)
                .filter(car -> car.getFuelType().equals(fuelType))
                .toList();
    }

    public List<CarModel> getCarsByPriceAscending() {
        return getCarList().stream().sorted((car1, car2) -> car1.getPrice().compareTo(car2.getPrice())).toList();
    }


    public List<CarModel> getCarsByPriceDescending() {
        return getCarList().stream().sorted(Comparator.comparing(CarModel::getPrice).reversed()).toList();
    }

    public List<CarModel> getCarsByPriceRange(BigDecimal priceMin, BigDecimal priceMax) {
        return getCarList().stream().filter(car -> car.getPrice().compareTo(priceMin) >= 0 && car.getPrice().compareTo(priceMax) <= 0).toList();
    }

    public List<CarModel> getCarsByBodyType (String bodyType) {
        return getCarList().stream()
                .filter(car -> car.getBody() != null)
                .filter(car -> car.getBody().equals(bodyType))
                .toList();
    }

    public List<CarModel> getCarsCheaperThan (BigDecimal upperPriceBorder) {
        return getCarList().stream()
                .filter(car -> car.getPrice() != null)
                .filter(car -> car.getPrice().compareTo(upperPriceBorder) < 0)
                .collect(toList());
    }

    public List<CarModel> getCarsMoreExpensiveThan (BigDecimal lowerPriceBorder) {
        return getCarList().stream()
                .filter(car -> car.getPrice() != null)
                .filter(car -> car.getPrice().compareTo(lowerPriceBorder) >= 0)
                .collect(toList());
    }

    public List<CarModel> getCarsWithHigherMileageThan(Integer mileage){
        return getCarList().stream()
                .filter(car -> car.getMileage() != null)
                .filter(car -> car.getMileage().compareTo(mileage) >= 0)
                .toList();
    }

    public List<CarModel> getCarsWithLowerMileageThan(Integer mileage){
        return getCarList().stream()
                .filter(car -> car.getMileage() != null)
                .filter(car -> car.getMileage().compareTo(mileage) <= 0)
                .toList();
    }

    public List<CarModel> getCarsWithinGivenMileage(Integer maxMileage, Integer minMileage){
        return getCarList().stream()
                .filter(car -> car.getMileage() != null)
                .filter(car -> car.getMileage().compareTo(minMileage) <= 0 && car.getMileage().compareTo(maxMileage) >= 0)
                .toList();
    }

    public List<CarModel> getCarsByColor(String color){
        return getCarList().stream()
                .filter(car -> car.getColor() != null)
                .filter(car -> car.getColor().equals(color))
                .toList();
    }

    public List<CarModel> getCarsByProductionDate(Integer productionDate){
        return getCarList().stream()
                .filter(car -> car.getProductionDate() != null)
                .filter(car -> car.getProductionDate().equals(productionDate))
                .toList();
    }

    public List<CarModel> getCarsNewerThan(Integer productionDate){
        return getCarList().stream()
                .filter(car ->car.getProductionDate() != null)
                .filter(car -> car.getProductionDate().compareTo(productionDate)>=0)
                .toList();
    }

    public List<CarModel> getCarsOlderThan(Integer productionDate){
        return getCarList().stream()
                .filter(car ->car.getProductionDate() != null)
                .filter(car -> car.getProductionDate().compareTo(productionDate)<=0)
                .toList();
    }



}
