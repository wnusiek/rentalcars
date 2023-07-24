package com.example.rentalcars.MenagementServices;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.repository.CarRepository;
import com.example.rentalcars.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CarsByPriceService {

    private final CarRepository carRepository;

    private final CarService carService;


    public List<CarModel> getCarsByPriceAscending() {
        return carService.getCarList().stream().sorted(Comparator.comparing(CarModel::getPrice)).toList();
    }


    public List<CarModel> getCarsByPriceDescending() {
        return carService.getCarList().stream().sorted(Comparator.comparing(CarModel::getPrice).reversed()).toList();
    }

    public List<CarModel> getCarsByPriceRange(BigDecimal priceMin, BigDecimal priceMax) {
        return carService.getCarList().stream().filter(car -> car.getPrice().compareTo(priceMin) >= 0 && car.getPrice().compareTo(priceMax) <= 0).toList();
    }

    public List<CarModel> getCarsCheaperThan(BigDecimal upperPriceBorder) {
        return carService.getCarList().stream()
                .filter(car -> car.getPrice() != null)
                .filter(car -> car.getPrice().compareTo(upperPriceBorder) < 0)
                .collect(toList());
    }

    public List<CarModel> getCarsMoreExpensiveThan(BigDecimal lowerPriceBorder) {
        return carService.getCarList().stream()
                .filter(car -> car.getPrice() != null)
                .filter(car -> car.getPrice().compareTo(lowerPriceBorder) >= 0)
                .collect(toList());
    }
}
