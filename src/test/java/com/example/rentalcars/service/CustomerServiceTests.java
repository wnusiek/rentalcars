package com.example.rentalcars.service;

import com.example.rentalcars.Exceptions.CustomerAdditionException;
import com.example.rentalcars.model.CustomerModel;
import com.example.rentalcars.model.RoleModel;
import com.example.rentalcars.model.UserModel;
import com.example.rentalcars.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {
    private CustomerRepository customerRepository;
    private CustomerService customerService;
    private CustomerModel customerModel1;
    private CustomerModel customerModel2;
    private CustomerModel customerModel3;
    private UserModel userModel;
    private RoleModel roleModel;

    @BeforeEach
    public void setup(){
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerService(customerRepository);
        roleModel = new RoleModel(1L, "USER");
        userModel = new UserModel(1L, "bolek","bolek1111","bolek@gmail.com", true, roleModel);
        customerModel1 = new CustomerModel(1L, "Bolesław", "Śmiały", "111111111", "qwerty1234", "33333333333", "Kraków", "11-111", userModel);
        customerModel2 = new CustomerModel(2L, "", "", "", "", "", "", "",
                new UserModel(2L, "Kolec", "", "", true, new RoleModel(2L, "ADMIN")));
        customerModel3 = new CustomerModel(3L, "", "", "", "", "", "", "",
                new UserModel(3L, "Stolec", "", "", true, new RoleModel(2L, "ADMIN")));
    }

    @Test
    public void testGetCustomerList_ReturnCustomerList() {
        given(customerRepository.findAll()).willReturn(List.of(customerModel1, customerModel2));
        var savedCustomerList = customerService.getCustomerList();
        assertThat(savedCustomerList.size()).isEqualTo(2);
    }

    @Test
    public void testGetCustomerList_ReturnEmptyList() {
        given(customerRepository.findAll()).willReturn(Collections.emptyList());
        var savedCustomerList = customerService.getCustomerList();
        assertThat(savedCustomerList).isEmpty();
    }

    @Test
    public void testFindWithFilter_NoCriteria_NoEmptyResult() {
        given(customerRepository.search(null)).willReturn(List.of(customerModel1, customerModel2, customerModel3));
        var savedCustomerList = customerService.findWithFilter(null);
        assertThat(savedCustomerList.size()).isEqualTo(3);
    }

    @Test
    public void testFindById_ReturnCustomer() {
        Long customerId1 = 1L;
        given(customerRepository.findById(customerId1)).willReturn(Optional.of(customerModel1));
        var savedCustomer = customerService.findById(customerId1);
        assertThat(savedCustomer).isNotNull().isEqualTo(customerModel1);
    }

    @Test
    public void testFindById_ExceptionThrown() {
        Long customerId1 = 1L;
        given(customerRepository.findById(customerId1)).willReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> customerService.findById(customerId1));
    }

    @Test
    public void testSaveCustomer_CustomerSaved(){
        given(customerRepository.save(customerModel1)).willReturn(customerModel1);
        var savedCustomer = customerService.saveCustomer(customerModel1);
        assertThat(savedCustomer).isNotNull().isEqualTo(customerModel1);
    }

    @Test
    public void testSaveCustomer_ExceptionThrown(){
        given(customerRepository.save(customerModel1)).willThrow(new RuntimeException());
        assertThrows(CustomerAdditionException.class, () -> customerService.saveCustomer(customerModel1));
    }

    @Test
    public void testGetCustomerByUserName_ReturnCustomer() {
        String userName = "Stolec";
        given(customerService.getCustomerList()).willReturn(List.of(customerModel1, customerModel2, customerModel3));
        var savedCustomer = customerService.getCustomerByUserName(userName);
        assertThat(savedCustomer.getUser().getName()).isEqualTo(userName);
    }

    @Test
    public void testGetCustomerByUserName_ExceptionThrown() {
        String userName = "Bolec";
        given(customerService.getCustomerList()).willThrow(new EntityNotFoundException());
        assertThrows(EntityNotFoundException.class, () -> customerService.getCustomerByUserName(userName));
    }
}
