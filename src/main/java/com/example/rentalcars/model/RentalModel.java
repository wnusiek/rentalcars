package com.example.rentalcars.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "date_of_rental")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfRental;

//    @OneToOne
    @Column(name = "reservation_id")
    private Long reservationId;

    @Column(name = "comments")
    private String comments;

}
