package com.example.rentalcars.service;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.repository.CarRepository;
import jdk.dynalink.linker.LinkerServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public void postAddCar(CarModel car){
        carRepository.save(car);
    }

    public List<CarModel> getCarList() {
        return carRepository.findAll();
    }

    public CarModel findById(Long id){
        return carRepository.findById(id).orElse(null);
    }

    public void updateCar(CarModel car) {
        carRepository.save(car);
    }

    public void removeCar(Long id) {
        carRepository.deleteById(id);
    }
}
