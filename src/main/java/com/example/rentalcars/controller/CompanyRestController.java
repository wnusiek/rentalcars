package com.example.rentalcars.controller;

import com.example.rentalcars.model.CompanyModel;
import com.example.rentalcars.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequiredArgsConstructor
@RestController

public class CompanyRestController {

    private final CompanyService companyService;
    @GetMapping("/company")
    public List<CompanyModel> getCompanyModelList() {
        return companyService.getCompanyModelList();
    }

    @PostMapping("/editCompany")
    public void edit(CompanyModel companyModel) {
        companyService.updateCompany(companyModel);
    }
}
