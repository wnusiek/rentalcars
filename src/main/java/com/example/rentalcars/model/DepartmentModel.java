package com.example.rentalcars.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @Column(name = "address")
    private String address;

    //lista pracowników placówki
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
    private Set<EmployeeModel> employees = new HashSet<>();

//    //lista aktualnie dostępnych aut
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
    private List<CarModel> cars = new ArrayList<>();


    public DepartmentModel(Long id, String city) {
        this.id = id;
        this.city = city;

    }
}
