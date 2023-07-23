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

    @GetMapping("/carRental")
    public List<CompanyModel> get() {
        return companyService.getCarRentalModelList();
    }

    @PostMapping("/addCarRental")
    public void add(CompanyModel companyModel) {
        companyService.postAddCarRental(companyModel);
    }

    @PostMapping("/editCarRental/{id}")
    public void edit(CompanyModel companyModel) {
        companyService.updateCarRental(companyModel);
    }

    @PostMapping("removeCarRental/{id}")
    public void removeCarRental(@PathVariable("id") Long id) {
        companyService.removeCarRental(id);
    }


}
