package com.example.rentalcars.DTO;

import com.example.rentalcars.enums.BodyType;
import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.enums.FuelType;
import com.example.rentalcars.enums.GearboxType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class CarDto {

    private Long id;

    private String mark;

    private String model;

    private CarStatus availability;

    private Integer mileage;

    private Integer productionDate;

    private BigDecimal price;

    private BodyType body;

    private String color;

    private FuelType fuelType;

    private GearboxType gearbox;
}
