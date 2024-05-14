package com.example.rentalcars.service;

import com.example.rentalcars.model.CompanyModel;
import com.example.rentalcars.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTests {
private CompanyService companyService;
private CompanyRepository companyRepository;
private CompanyModel companyModel;

    @BeforeEach
    public void setUp(){
        companyRepository = mock(CompanyRepository.class);
        companyService = new CompanyService(companyRepository);
        companyModel = new CompanyModel(1L, "Nazwa", "URL", "Adres", "Właściciel", "Logotyp");
    }

    @Test
    public void testUpdateCompany_Success(){
        when(companyRepository.save(companyModel)).thenReturn(companyModel);
        CompanyModel savedCompany = companyService.updateCompany(companyModel);
        assertThat(savedCompany).isNotNull();
    }

    @Test
    public void testGetCompanyName_ReturnName(){
        given(companyRepository.findAll()).willReturn(List.of(companyModel));
        String companyName = companyService.getCompanyName();
        assertThat(companyName).isEqualTo(companyModel.getCompanyName());
    }

    @Test
    public void testGetCompanyName_ReturnEmptyName(){
        given(companyRepository.findAll()).willReturn(Collections.emptyList());
        String companyName = companyService.getCompanyName();
        assertThat(companyName).isEqualTo("");
    }
}
