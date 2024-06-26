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

    public List<CompanyModel> getCompanyModelList() {
        return companyRepository.findAll();
    }

    public CompanyModel updateCompany(CompanyModel companyModel) {
        return companyRepository.save(companyModel);
    }

    public String getCompanyName() {
        return getCompanyModelList().isEmpty() ? "" : getCompanyModelList().get(0).getCompanyName();
    }
}
