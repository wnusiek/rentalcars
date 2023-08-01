package com.example.rentalcars.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ReturnModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeModel employee;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfReturn;

    @OneToOne
    private ReservationModel reservation;

    @Column(name = "supplement")
    private BigDecimal supplement;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "comments")
    private String comments;
}
