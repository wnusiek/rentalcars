package com.example.rentalcars.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class RentalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeModel employee;

    @Column(name = "date_of_rental")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfRental;

    @OneToOne
    private ReservationModel reservation;

    @Column(name = "comments")
    private String comments;

}
