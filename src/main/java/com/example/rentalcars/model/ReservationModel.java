package com.example.rentalcars.model;

import com.example.rentalcars.DTO.ReservationDto;
import com.example.rentalcars.service.ReservationService;
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
public class ReservationModel {


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

//    @Column(name = "reception_venue")
    @ManyToOne
    @JoinColumn(name = "receptionVenue_id")
    private DepartmentModel receptionVenue;

//    @Column(name = "return_venue")
    @ManyToOne
    @JoinColumn(name = "returnVenue_id")
    private DepartmentModel returnVenue;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerModel customer;

    public String getReservationInfo(){
        return car.getMark() + " " + car.getModel() + " | " + dateFrom.toString() + " - " + dateTo.toString();
    }

}
