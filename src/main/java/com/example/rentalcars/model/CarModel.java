package com.example.rentalcars.model;

import com.example.rentalcars.enums.BodyType;
import com.example.rentalcars.enums.CarStatus;
import com.example.rentalcars.enums.FuelType;
import com.example.rentalcars.enums.GearboxType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

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
    private BodyType body;

    @Column(name = "gearbox")
    private GearboxType gearbox;

    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    @Column(name = "number_of_doors")
    private Integer numberOfDoors;

    @Column(name = "fuel_type")
    private FuelType fuelType;

    @Column(name = "trunk")
    private String trunk;

    @Column(name = "availability")
    private CarStatus availability;

    @Column(name = "color")
    private String color;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "production_date")
    private Integer productionDate;

    public String getCarInfo(){
        return mark + " " + model + " " + productionDate.toString();
    }

//    @ManyToOne
//    public DepartmentModel department;
}
