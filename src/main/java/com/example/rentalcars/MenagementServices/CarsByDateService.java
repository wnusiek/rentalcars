package com.example.rentalcars.MenagementServices;

import com.example.rentalcars.DTO.CarDto;
import com.example.rentalcars.repository.CarRepository;
import com.example.rentalcars.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarsByDateService {

    private final CarRepository carRepository;

    private final CarService carService;

    public List<CarDto> getCarsByProductionDate(Integer productionDate) {
        return carService.getCarList().stream()
                .filter(car -> car.getProductionDate() != null)
                .filter(car -> car.getProductionDate().equals(productionDate))
                .toList();
    }

    public List<CarDto> getCarsNewerThan(Integer productionDate) {
        return carService.getCarList().stream()
                .filter(car -> car.getProductionDate() != null)
                .filter(car -> car.getProductionDate().compareTo(productionDate) >= 0)
                .toList();
    }

    public List<CarDto> getCarsOlderThan(Integer productionDate) {
        return carService.getCarList().stream()
                .filter(car -> car.getProductionDate() != null)
                .filter(car -> car.getProductionDate().compareTo(productionDate) <= 0)
                .toList();
    }
}
