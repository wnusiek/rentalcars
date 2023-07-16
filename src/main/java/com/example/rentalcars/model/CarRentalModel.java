package com.example.rentalcars.model;

import com.example.rentalcars.service.DepartmentService;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class CarRentalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "car_rental_name")
    private String carRentalName;

    @Column(name = "car_rental_domain_url")
    private String carRentalDomainURL;

    @Column(name = "car_rental_address")
    private String carRentalAddress;

    @Column(name = "car_rental_owner")
    private String carRentalOwner;

    @Column(name = "car_rental_logotype")
    private String carRentalLogotype;

    @OneToMany
    private List <DepartmentModel> departmentModels = new ArrayList<>();

}
