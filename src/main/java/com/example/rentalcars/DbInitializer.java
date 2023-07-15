package com.example.rentalcars;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.repository.CarRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DbInitializer implements ApplicationRunner {
    private final CarRepository carRepository;

    public DbInitializer(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        carRepository.save(new CarModel(1l, "Opel", "Corsa", BigDecimal.ONE, "500", "Sedan", "automatyczna", 5, 4, "benzyna", "aaa", true));
    }
}
