package com.example.rentalcars.model;

import com.example.rentalcars.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class SettlementModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private CarModel car;

    @Column(name = "date_from")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;

    @Column(name = "date_to")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "receptionVenue_id")
    private DepartmentModel receptionVenue;

    @ManyToOne
    @JoinColumn(name = "returnVenue_id")
    private DepartmentModel returnVenue;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerModel customer;

    @Column(name = "comments")
    private String comments;

    @Column(name = "status")
    private OrderStatus status;

}
