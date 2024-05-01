package com.example.rentalcars.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
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

    @OneToOne(cascade = CascadeType.MERGE)
    private ReservationModel reservation;

    @Column(name = "supplement")
    private BigDecimal supplement;

    @Column(name = "total_cost")
    private BigDecimal totalCost;

    @Column(name = "comments")
    private String comments;
}
