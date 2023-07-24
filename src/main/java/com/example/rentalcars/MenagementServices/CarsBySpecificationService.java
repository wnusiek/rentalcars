package com.example.rentalcars.MenagementServices;

import com.example.rentalcars.DTO.CarDto;
import com.example.rentalcars.enums.BodyType;
import com.example.rentalcars.enums.FuelType;
import com.example.rentalcars.enums.GearboxType;
import com.example.rentalcars.repository.CarRepository;
import com.example.rentalcars.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarsBySpecificationService {

    private final CarRepository carRepository;

    private final CarService carService;

    public List<CarDto> getCarsByGearbox(GearboxType gearbox) {
        return carService.getCarList().stream()
                .filter(car -> car.getGearbox() != null)
                .filter(car -> car.getGearbox().equals(gearbox))
                .toList();
    }

    public List<CarDto> getCarsByMark(String mark) {
        return carService.getCarList().stream()
                .filter(car -> car.getMark() != null)
                .filter(car -> car.getMark().equals(mark))
                .toList();
    }

    public List<CarDto> getCarsByBodyType(BodyType bodyType) {
        return carService.getCarList().stream()
                .filter(car -> car.getBody() != null)
                .filter(car -> car.getBody().equals(bodyType))
                .toList();
    }

    public List<CarDto> getCarsByColor(String color) {
        return carService.getCarList().stream()
                .filter(car -> car.getColor() != null)
                .filter(car -> car.getColor().equals(color))
                .toList();
    }

    public List<CarDto> getCarsByFuelType(FuelType fuelType) {
        return carService.getCarList().stream()
                .filter(car -> car.getFuelType() != null)
                .filter(car -> car.getFuelType().equals(fuelType))
                .toList();
    }

}
