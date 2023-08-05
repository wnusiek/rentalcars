package com.example.rentalcars.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "driver_licence_number")
    private String driverLicenceNumber;

    @Email
    @Column(name = "email")
    private String email;

    @Column(name = "pesel")
    private String pesel;

    @Column(name = "city")
    private String city;

    @Column(name = "zip_code")
    private String zipCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserModel user;

    public String getName(){
        return firstName + " " + lastName;
    }
}
