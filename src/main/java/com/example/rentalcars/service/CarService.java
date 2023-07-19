package com.example.rentalcars.service;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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


    public List<CarModel> getAvailableCars(){
        return getCarList().stream()
                .filter(car -> car.getAvailability() != null)
                .filter(car -> car.getAvailability() == true)
                .toList();
    }

    public List<CarModel> getCarsByGearbox(String gearbox) {
        return getCarList().stream()
                .filter(car -> car.getGearbox().equals(gearbox))
                .toList();
    }
}
