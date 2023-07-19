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


    @Column
    private Long employeeId;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfReturn;


    @Column
    private Long reservationId;

    @Column
    private String comments;
}
