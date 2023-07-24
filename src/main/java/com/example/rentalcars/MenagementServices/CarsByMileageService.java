package com.example.rentalcars.MenagementServices;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.repository.CarRepository;
import com.example.rentalcars.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarsByMileageService {

    private final CarRepository carRepository;

    private final CarService carService;

    public List<CarModel> getCarsWithHigherMileageThan(Integer mileage) {
        return carService.getCarList().stream()
                .filter(car -> car.getMileage() != null)
                .filter(car -> car.getMileage().compareTo(mileage) >= 0)
                .toList();
    }

    public List<CarModel> getCarsWithLowerMileageThan(Integer mileage) {
        return carService.getCarList().stream()
                .filter(car -> car.getMileage() != null)
                .filter(car -> car.getMileage().compareTo(mileage) <= 0)
                .toList();
    }

    public List<CarModel> getCarsWithinGivenMileage(Integer maxMileage, Integer minMileage) {
        return carService.getCarList().stream()
                .filter(car -> car.getMileage() != null)
                .filter(car -> car.getMileage().compareTo(minMileage) <= 0 && car.getMileage().compareTo(maxMileage) >= 0)
                .toList();
    }
}
