package com.example.rentalcars.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(name = "color")
    private String color;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "production_date")
    private Integer productionDate;

//    @ManyToOne
//    public DepartmentModel department;
}
