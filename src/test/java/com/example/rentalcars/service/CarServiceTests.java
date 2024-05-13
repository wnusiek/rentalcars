package com.example.rentalcars.service;

import com.example.rentalcars.enums.BodyType;
import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.enums.FuelType;
import com.example.rentalcars.enums.GearboxType;
import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceTests {
    private CarService carService;
    private CarRepository carRepository;
    private CarModel carModel;
    @BeforeEach
    public void setUp(){
        carRepository = mock(CarRepository.class);
        carService = new CarService(carRepository);
        carModel = new CarModel(1l, "Opel", "Corsa", BigDecimal.valueOf(100), BigDecimal.valueOf(500), BodyType.HATCHBACK, GearboxType.AUTOMATIC, 5, 4, FuelType.PETROL, "aaa", CarStatus.AVAILABLE, "Red", 128000, 2004);
    }

    @Test
    public void givenCarModel_whenSaveCar_thenReturnTrue(){
        when(carRepository.save(carModel)).thenReturn(carModel);
        CarModel savedCar = carService.saveCar(carModel);
        assertThat(savedCar).isNotNull();
    }

    @Test
    public void givenCarModel_whenSaveCar_thenReturnFalse(){
        when(carRepository.save(carModel)).thenReturn(null);
        CarModel savedCar = carService.saveCar(carModel);
        assertThat(savedCar).isNull();
    }

    @Test
    public void givenCarModel_whenSaveCar_thenReturnException(){
        when(carRepository.save(carModel)).thenThrow(new RuntimeException("Błąd"));
        CarModel savedCar = carService.saveCar(carModel);
        assertThat(savedCar).isNull();
    }

}
