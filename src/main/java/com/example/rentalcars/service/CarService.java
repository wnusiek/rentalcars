package com.example.rentalcars.service;

import com.example.rentalcars.enums.BodyType;
import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.enums.FuelType;
import com.example.rentalcars.enums.GearboxType;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

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
                .filter(car -> car.getAvailability().equals(CarStatus.AVAILABLE))
                .toList();
    }

    public void setCarStatus(Long id, CarStatus carStatus) {
        var car = carRepository.findById(id);
        if (car.isPresent()) {
            var c = car.get();
            c.setAvailability(carStatus);
            carRepository.save(c);
        }
    }




}
