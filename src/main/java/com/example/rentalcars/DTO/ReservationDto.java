package com.example.rentalcars.DTO;

import com.example.rentalcars.model.CarModel;
import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.ReservationModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class ReservationDto {

    private CarModel car;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    private BigDecimal price;

    private String receptionVenue;

    private String returnVenue;

    private CustomerModel customer;

    }
