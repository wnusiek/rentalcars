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
public class ReturnModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    @Column
    private EmployeeModel employee;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfReturn;

    @OneToOne
    @Column
    private ReservationModel reservation;

    @Column
    private String comments;
}
