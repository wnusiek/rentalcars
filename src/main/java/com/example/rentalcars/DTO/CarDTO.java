package com.example.rentalcars.DTO;

import com.example.rentalcars.enums.CarStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CarDTO {

    private String mark;
    private String model;
    private CarStatus availability;

}
