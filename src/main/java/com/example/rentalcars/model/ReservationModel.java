package com.example.rentalcars.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ReservationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_id")
    private String car_id;

    @Column(name = "date_to")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date date_to;

    @Column(name = "date_from")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date date_from;

    @Column(name = "price")
    public BigDecimal price;

    @Column(name = "reception_venue")
    public String reception_venue;
}