package com.example.rentalcars.model;

import com.example.rentalcars.DTO.EmployeeDto;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "employees")
    private Set<EmployeeModel> employees = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "cars")
    private List<CarModel> cars = new ArrayList<>();


    public DepartmentModel(Long id, String city) {
        this.id = id;
        this.city = city;

    }
}
