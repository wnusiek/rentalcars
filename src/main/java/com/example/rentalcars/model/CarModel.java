package com.example.rentalcars.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mark")
    private String mark;

    @Column(name = "model")
    private String model;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "bail")
    private BigDecimal bail;

    @Column(name = "body")
    private String body;

    @Column(name = "gearbox")
    private String gearbox;

    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    @Column(name = "number_of_doors")
    private Integer numberOfDoors;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "trunk")
    private String trunk;

    @Column(name = "availability")
    private Boolean availability;

    @Column(name = "date_of_production")
    @DateTimeFormat(pattern = "yyyy")
    private Date dateOfProduction;

    @Column(name = "car_mileage")
    private Integer carMileage;

    @Column(name = "carColor")
    private String color;

}
