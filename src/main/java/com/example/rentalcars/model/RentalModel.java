package com.example.rentalcars.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class RentalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    @Column(name = "employee")
//    private EmployeeModel employee;
//
//    @Column(name = "date_from")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    private Date dateFrom;
//
//    @OneToOne
//    @Column(name = "reservation")
//    private ReservationModel reservation;
//
//    @Column(name = "comments")
//    private String comments;

}
