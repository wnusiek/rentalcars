package com.example.rentalcars.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class DepartmentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    private String city;

    //lista pracowników placówki
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
    private Set<EmployeeModel> employees = new HashSet<>();

    //lista aktualnie dostępnych aut
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
    private Set<CarModel> cars = new HashSet<>();
}
