package com.example.rentalcars.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "driver_license_number")
    private String driverLicenseNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "pesel")
    private String pesel;

    @Column(name = "city")
    private String city;

    @Column(name = "zip_code")
    private String zipCode;


}
