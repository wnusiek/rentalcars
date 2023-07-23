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
public class CompanyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_domain_url")
    private String companyDomainURL;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "company_owner")
    private String companyOwner;

    @Column(name = "company_logotype")
    private String companyLogotype;

}
