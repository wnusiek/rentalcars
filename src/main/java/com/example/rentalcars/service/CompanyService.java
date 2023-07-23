package com.example.rentalcars.service;

import com.example.rentalcars.model.CompanyModel;
import com.example.rentalcars.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;


    public void postAddCarRental(CompanyModel companyModel) {
        companyRepository.save(companyModel);
    }

    public List<CompanyModel> getCarRentalModelList() {
        return companyRepository.findAll();
    }

    public CompanyModel findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    public void updateCarRental(CompanyModel companyModel) {
        companyRepository.save(companyModel);
    }

    public void removeCarRental(Long id) {
        companyRepository.deleteById(id);
    }
}
